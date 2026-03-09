package dr.sbs.admin.service;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import dr.sbs.admin.dao.AdminUserRoleRelationDao;
import dr.sbs.mp.entity.AdminResource;
import dr.sbs.mp.entity.AdminUser;
import dr.sbs.mp.entity.AdminUserRoleRelation;
import dr.sbs.mp.service.AdminUserRoleRelationMpService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** AdminUserCacheService实现类 */
@Service
public class AdminUserCacheServiceImpl implements AdminUserCacheService {
  @Autowired private AdminUserService userService;
  @Autowired private RedisService redisService;
  @Autowired private AdminUserRoleRelationMpService userRoleRelationMpService;
  @Autowired private AdminUserRoleRelationDao userRoleRelationDao;

  private String redisDatabase = "sbsAdmin";
  // 24 hours
  private Integer redisExpire = 86400;
  private String redisKeyAdmin = "admin";
  private String redisKeyResourceList = "resourceList";

  @Override
  public void delUser(Integer userId) {
    AdminUser user = userService.getItem(userId);
    if (user != null) {
      String key = redisDatabase + ":" + redisKeyAdmin + ":" + user.getUsername();
      redisService.del(key);
    }
  }

  @Override
  public void delResourceList(Integer userId) {
    String key = redisDatabase + ":" + redisKeyResourceList + ":" + userId;
    redisService.del(key);
  }

  @Override
  public void delResourceListByRole(Integer roleId) {
    QueryWrapper<AdminUserRoleRelation> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("role_id", roleId);
    queryWrapper.eq("status", 1);
    List<AdminUserRoleRelation> relationList = userRoleRelationMpService.list(queryWrapper);
    if (CollUtil.isNotEmpty(relationList)) {
      String keyPrefix = redisDatabase + ":" + redisKeyResourceList + ":";
      List<String> keys =
          relationList.stream()
              .map(relation -> keyPrefix + relation.getUserId())
              .collect(Collectors.toList());
      redisService.del(keys);
    }
  }

  @Override
  public void delResourceListByRoleIds(List<Integer> roleIds) {
    QueryWrapper<AdminUserRoleRelation> queryWrapper = new QueryWrapper<>();
    queryWrapper.in("role_id", roleIds);
    queryWrapper.eq("status", 1);
    List<AdminUserRoleRelation> relationList = userRoleRelationMpService.list(queryWrapper);
    if (CollUtil.isNotEmpty(relationList)) {
      String keyPrefix = redisDatabase + ":" + redisKeyResourceList + ":";
      List<String> keys =
          relationList.stream()
              .map(relation -> keyPrefix + relation.getUserId())
              .collect(Collectors.toList());
      redisService.del(keys);
    }
  }

  @Override
  public void delResourceListByResource(Integer resourceId) {
    List<Integer> userIdList = userRoleRelationDao.getAdminIdList(resourceId);
    if (CollUtil.isNotEmpty(userIdList)) {
      String keyPrefix = redisDatabase + ":" + redisKeyResourceList + ":";
      List<String> keys =
          userIdList.stream().map(userId -> keyPrefix + userId).collect(Collectors.toList());
      redisService.del(keys);
    }
  }

  @Override
  public AdminUser getUser(String username) {
    String key = redisDatabase + ":" + redisKeyAdmin + ":" + username;
    return (AdminUser) redisService.get(key);
  }

  @Override
  public void setUser(AdminUser adminUser) {
    String key = redisDatabase + ":" + redisKeyAdmin + ":" + adminUser.getUsername();
    redisService.set(key, adminUser, redisExpire);
  }

  @Override
  public List<AdminResource> getResourceList(Integer userId) {
    String key = redisDatabase + ":" + redisKeyResourceList + ":" + userId;
    return (List<AdminResource>) redisService.get(key);
  }

  @Override
  public void setResourceList(Integer userId, List<AdminResource> resourceList) {
    String key = redisDatabase + ":" + redisKeyResourceList + ":" + userId;
    redisService.set(key, resourceList, redisExpire);
  }
}
