package dr.sbs.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import dr.sbs.admin.dto.AdminUpdatePasswordParam;
import dr.sbs.admin.dto.AdminUserParam;
import dr.sbs.mp.entity.AdminMenu;
import dr.sbs.mp.entity.AdminResource;
import dr.sbs.mp.entity.AdminRole;
import dr.sbs.mp.entity.AdminUser;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

/** 后台管理员Service */
public interface AdminUserService {
  /** 根据用户名获取后台管理员 */
  AdminUser getUserByUsername(String username);

  /** 注册功能 */
  AdminUser register(AdminUserParam adminUserParam);

  /**
   * 登录功能
   *
   * @param username 用户名
   * @param password 密码
   * @return 生成的JWT的token
   */
  String login(String username, String password);

  /**
   * 刷新token的功能
   *
   * @param oldToken 旧的token
   */
  String refreshToken(String oldToken);

  /** 根据用户id获取用户 */
  AdminUser getItem(Integer id);

  /** 根据用户名或昵称分页查询用户 */
  Page<AdminUser> list(String keyword, Integer pageSize, Integer pageNum);

  /** 修改指定用户信息 */
  boolean update(Integer id, AdminUser adminUser);

  /** 删除指定用户 */
  boolean delete(Integer id);

  /** 修改用户角色关系 */
  @Transactional
  boolean updateRole(Integer userId, List<Integer> roleIds);

  /** 获取用户对于角色 */
  List<AdminRole> getRoleList(Integer userId);

  /** 获取指定用户的可访问资源 */
  List<AdminResource> getResourceList(Integer userId);

  /** 根据管理员ID获取对应菜单 */
  List<AdminMenu> getMenuList(Integer userId);

  /** 修改密码 */
  int updatePassword(AdminUpdatePasswordParam updatePasswordParam);

  /** 获取用户信息 */
  UserDetails loadUserDetailsByUsername(String username);
}
