package dr.sbs.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import dr.sbs.admin.dto.AdminMenuNode;
import dr.sbs.mp.entity.AdminMenu;
import java.util.List;

/** 后台菜单管理Service */
public interface AdminMenuService {
  /** 创建后台菜单 */
  boolean create(AdminMenu adminMenu);

  /** 修改后台菜单 */
  boolean update(Integer id, AdminMenu adminMenu);

  /** 根据ID获取菜单详情 */
  AdminMenu getItem(Integer id);

  /** 根据ID删除菜单 */
  boolean delete(Integer id);

  /** 分页查询后台菜单 */
  Page<AdminMenu> list(Integer parentId, Integer pageSize, Integer pageNum);

  /** 树形结构返回所有菜单列表 */
  List<AdminMenuNode> treeList();

  /** 修改菜单显示状态 */
  boolean updateHidden(Integer id, Integer hidden);
}
