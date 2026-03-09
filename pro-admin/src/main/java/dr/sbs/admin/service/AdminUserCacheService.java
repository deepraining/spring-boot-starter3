package dr.sbs.admin.service;

import dr.sbs.mp.entity.AdminResource;
import dr.sbs.mp.entity.AdminUser;
import java.util.List;

/** 后台用户缓存操作类 */
public interface AdminUserCacheService {
  /** 删除后台用户缓存 */
  void delUser(Integer userId);

  /** 删除后台用户资源列表缓存 */
  void delResourceList(Integer userId);

  /** 当角色相关资源信息改变时删除相关后台用户缓存 */
  void delResourceListByRole(Integer roleId);

  /** 当角色相关资源信息改变时删除相关后台用户缓存 */
  void delResourceListByRoleIds(List<Integer> roleIds);

  /** 当资源信息改变时，删除资源项目后台用户缓存 */
  void delResourceListByResource(Integer resourceId);

  /** 获取缓存后台用户信息 */
  AdminUser getUser(String username);

  /** 设置缓存后台用户信息 */
  void setUser(AdminUser adminUser);

  /** 获取缓存后台用户资源列表 */
  List<AdminResource> getResourceList(Integer userId);

  /** 设置后台后台用户资源列表 */
  void setResourceList(Integer userId, List<AdminResource> resourceList);
}
