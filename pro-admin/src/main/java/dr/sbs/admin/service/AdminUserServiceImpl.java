package dr.sbs.admin.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import dr.sbs.admin.bo.AdminUserDetails;
import dr.sbs.admin.dao.AdminUserRoleRelationDao;
import dr.sbs.admin.dto.AdminUpdatePasswordParam;
import dr.sbs.admin.dto.AdminUserParam;
import dr.sbs.admin.util.JwtTokenUtil;
import dr.sbs.common.exception.ApiAssert;
import dr.sbs.mp.entity.AdminLoginLog;
import dr.sbs.mp.entity.AdminMenu;
import dr.sbs.mp.entity.AdminResource;
import dr.sbs.mp.entity.AdminRole;
import dr.sbs.mp.entity.AdminUser;
import dr.sbs.mp.entity.AdminUserRoleRelation;
import dr.sbs.mp.service.AdminLoginLogMpService;
import dr.sbs.mp.service.AdminUserMpService;
import dr.sbs.mp.service.AdminUserRoleRelationMpService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/** AdminUserService实现类 */
@Service
@Slf4j
public class AdminUserServiceImpl implements AdminUserService {
  @Autowired private JwtTokenUtil jwtTokenUtil;
  @Autowired private PasswordEncoder passwordEncoder;
  @Autowired private AdminUserMpService userMpService;
  @Autowired private AdminUserRoleRelationMpService roleRelationMpService;
  @Autowired private AdminUserRoleRelationDao roleRelationDao;
  @Autowired private AdminLoginLogMpService loginLogMpService;
  @Autowired private AdminUserCacheService userCacheService;

  @Override
  public AdminUser getUserByUsername(String username) {
    AdminUser adminUser = userCacheService.getUser(username);
    if (adminUser != null) return adminUser;
    QueryWrapper<AdminUser> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("username", username);
    queryWrapper.ne("status", -1);
    List<AdminUser> adminList = userMpService.list(queryWrapper);
    if (adminList != null && adminList.size() > 0) {
      adminUser = adminList.get(0);
      if (adminUser.getStatus() == 1) {
        userCacheService.setUser(adminUser);
        return adminUser;
      }
    }
    return null;
  }

  @Override
  public AdminUser register(AdminUserParam adminUserParam) {
    AdminUser adminUser = new AdminUser();
    BeanUtils.copyProperties(adminUserParam, adminUser);
    adminUser.setStatus(1);
    // 查询是否有相同用户名的用户
    QueryWrapper<AdminUser> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("username", adminUser.getUsername());
    queryWrapper.ne("status", -1);
    List<AdminUser> adminUserList = userMpService.list(queryWrapper);
    if (adminUserList.size() > 0) {
      ApiAssert.fail("该用户名已被注册");
    }
    // 将密码进行加密操作
    String encodePassword = passwordEncoder.encode(adminUser.getPassword());
    adminUser.setPassword(encodePassword);
    userMpService.save(adminUser);
    return adminUser;
  }

  @Override
  public String login(String username, String password) {
    String token = null;
    // 密码需要客户端加密后传递
    try {
      UserDetails userDetails = loadUserDetailsByUsername(username);
      if (!passwordEncoder.matches(password, userDetails.getPassword())) {
        throw new BadCredentialsException("密码不正确");
      }
      UsernamePasswordAuthenticationToken authentication =
          new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(authentication);
      token = jwtTokenUtil.generateToken(userDetails);
      updateLoginTimeByUsername(username);
      insertLoginLog(username);
    } catch (AuthenticationException e) {
      log.warn("登录异常:{}", e.getMessage());
    }
    return token;
  }

  /**
   * 添加登录记录
   *
   * @param username 用户名
   */
  private void insertLoginLog(String username) {
    AdminUser user = getUserByUsername(username);
    if (user == null) return;
    AdminLoginLog loginLog = new AdminLoginLog();
    loginLog.setUserId(user.getId());
    loginLog.setUsername(username);
    loginLog.setNickname(user.getNickname());
    ServletRequestAttributes attributes =
        (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    HttpServletRequest request = attributes.getRequest();

    String ip = request.getRemoteAddr();
    if (!StringUtils.isEmpty(request.getHeader("x-real-ip"))) ip = request.getHeader("x-real-ip");
    else if (!StringUtils.isEmpty(request.getHeader("X-Real-IP")))
      ip = request.getHeader("X-Real-IP");

    loginLog.setIp(ip);
    loginLog.setUserAgent(request.getHeader("User-Agent"));
    loginLogMpService.save(loginLog);
  }

  /** 根据用户名修改登录时间 */
  private void updateLoginTimeByUsername(String username) {
    AdminUser record = new AdminUser();
    record.setLastLoginTime(LocalDateTime.now());
    QueryWrapper<AdminUser> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("username", username);
    userMpService.update(record, queryWrapper);
  }

  @Override
  public String refreshToken(String oldToken) {
    return jwtTokenUtil.refreshHeadToken(oldToken);
  }

  @Override
  public AdminUser getItem(Integer id) {
    return userMpService.getById(id);
  }

  @Override
  public Page<AdminUser> list(String keyword, Integer pageSize, Integer pageNum) {
    Page<AdminUser> page = new Page<>();
    page.setCurrent(pageNum);
    page.setSize(pageSize);
    QueryWrapper<AdminUser> queryWrapper = new QueryWrapper<>();
    queryWrapper.ne("status", -1);
    if (!StringUtils.isEmpty(keyword)) {
      queryWrapper.like("username", keyword).or().ne("status", -1).like("nickname", keyword);
    }
    return userMpService.page(page, queryWrapper);
  }

  @Override
  public boolean update(Integer id, AdminUser adminUser) {
    adminUser.setId(id);
    AdminUser rawUser = userMpService.getById(id);
    if (rawUser.getPassword().equals(adminUser.getPassword())) {
      // 与原加密密码相同的不需要修改
      adminUser.setPassword(null);
    } else {
      // 与原加密密码不同的需要加密修改
      if (StrUtil.isEmpty(adminUser.getPassword())) {
        adminUser.setPassword(null);
      } else {
        adminUser.setPassword(passwordEncoder.encode(adminUser.getPassword()));
      }
    }
    // 账户名不能更改
    adminUser.setUsername(null);
    boolean result = userMpService.updateById(adminUser);
    userCacheService.delUser(id);
    return result;
  }

  @Override
  public boolean delete(Integer id) {
    userCacheService.delUser(id);
    AdminUser adminUser = new AdminUser();
    adminUser.setId(id);
    adminUser.setStatus(-1);
    boolean result = userMpService.updateById(adminUser);
    userCacheService.delResourceList(id);
    return result;
  }

  @Override
  public boolean updateRole(Integer userId, List<Integer> roleIds) {
    // 先删除原来的关系
    QueryWrapper<AdminUserRoleRelation> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("user_id", userId);
    AdminUserRoleRelation adminUserRoleRelation = new AdminUserRoleRelation();
    adminUserRoleRelation.setStatus(-1);
    roleRelationMpService.update(adminUserRoleRelation, queryWrapper);
    // 建立新关系
    if (!CollectionUtils.isEmpty(roleIds)) {
      List<AdminUserRoleRelation> list = new ArrayList<>();
      for (Integer roleId : roleIds) {
        AdminUserRoleRelation roleRelation = new AdminUserRoleRelation();
        roleRelation.setUserId(userId);
        roleRelation.setRoleId(roleId);
        list.add(roleRelation);
      }
      roleRelationDao.insertList(list);
    }
    userCacheService.delResourceList(userId);
    return true;
  }

  @Override
  public List<AdminRole> getRoleList(Integer userId) {
    return roleRelationDao.getRoleList(userId);
  }

  @Override
  public List<AdminResource> getResourceList(Integer userId) {
    List<AdminResource> resourceList = userCacheService.getResourceList(userId);
    if (CollUtil.isNotEmpty(resourceList)) {
      return resourceList;
    }
    resourceList = roleRelationDao.getResourceList(userId);
    if (CollUtil.isNotEmpty(resourceList)) {
      userCacheService.setResourceList(userId, resourceList);
    }
    return resourceList;
  }

  @Override
  public List<AdminMenu> getMenuList(Integer userId) {
    return roleRelationDao.getMenuList(userId);
  }

  @Override
  public int updatePassword(AdminUpdatePasswordParam param) {
    if (StrUtil.isEmpty(param.getUsername())
        || StrUtil.isEmpty(param.getOldPassword())
        || StrUtil.isEmpty(param.getNewPassword())) {
      return -1;
    }
    QueryWrapper<AdminUser> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("username", param.getUsername());
    queryWrapper.ne("status", -1);
    List<AdminUser> adminList = userMpService.list(queryWrapper);
    if (CollUtil.isEmpty(adminList)) {
      return -2;
    }
    AdminUser adminUser = adminList.get(0);
    if (!passwordEncoder.matches(param.getOldPassword(), adminUser.getPassword())) {
      return -3;
    }
    adminUser.setPassword(passwordEncoder.encode(param.getNewPassword()));
    userMpService.updateById(adminUser);
    userCacheService.delUser(adminUser.getId());
    return 1;
  }

  @Override
  public UserDetails loadUserDetailsByUsername(String username) {
    // 获取用户信息
    AdminUser adminUser = getUserByUsername(username);
    if (adminUser != null) {
      List<AdminResource> resourceList = getResourceList(adminUser.getId());
      return new AdminUserDetails(adminUser, resourceList);
    }
    throw new UsernameNotFoundException("用户名或密码错误");
  }
}
