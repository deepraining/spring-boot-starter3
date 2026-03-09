package dr.sbs.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import dr.sbs.mp.entity.AdminMenu;
import dr.sbs.mp.entity.AdminResource;
import dr.sbs.mp.entity.AdminRole;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/** 后台角色管理Service */
public interface AdminRoleService {
  /** 添加角色 */
  boolean create(AdminRole role);

  /** 修改角色信息 */
  boolean update(Integer id, AdminRole role);

  /** 批量删除角色 */
  boolean delete(List<Integer> ids);

  /** 获取所有角色列表 */
  List<AdminRole> list();

  /** 分页获取角色列表 */
  Page<AdminRole> list(String keyword, Integer pageSize, Integer pageNum);

  /** 获取角色相关菜单 */
  List<AdminMenu> listMenu(Integer roleId);

  /** 获取角色相关资源 */
  List<AdminResource> listResource(Integer roleId);

  /** 给角色分配菜单 */
  @Transactional
  boolean allocMenu(Integer roleId, List<Integer> menuIds);

  /** 给角色分配资源 */
  @Transactional
  boolean allocResource(Integer roleId, List<Integer> resourceIds);
}
