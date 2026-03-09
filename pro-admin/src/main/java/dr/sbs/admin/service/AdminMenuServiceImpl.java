package dr.sbs.admin.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import dr.sbs.admin.dto.AdminMenuNode;
import dr.sbs.mp.entity.AdminMenu;
import dr.sbs.mp.service.AdminMenuMpService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** 后台菜单管理Service实现类 */
@Service
public class AdminMenuServiceImpl implements AdminMenuService {
  @Autowired private AdminMenuMpService menuMpService;

  @Override
  public boolean create(AdminMenu adminMenu) {
    updateLevel(adminMenu);
    return menuMpService.save(adminMenu);
  }

  /** 修改菜单层级 */
  private void updateLevel(AdminMenu adminMenu) {
    if (adminMenu.getParentId() == 0) {
      // 没有父菜单时为一级菜单
      adminMenu.setLevel(0);
    } else {
      // 有父菜单时选择根据父菜单level设置
      AdminMenu parentMenu = menuMpService.getById(adminMenu.getParentId());
      if (parentMenu != null) {
        adminMenu.setLevel(parentMenu.getLevel() + 1);
      } else {
        adminMenu.setLevel(0);
      }
    }
  }

  @Override
  public boolean update(Integer id, AdminMenu adminMenu) {
    adminMenu.setId(id);
    updateLevel(adminMenu);
    return menuMpService.updateById(adminMenu);
  }

  @Override
  public AdminMenu getItem(Integer id) {
    return menuMpService.getById(id);
  }

  @Override
  public boolean delete(Integer id) {
    AdminMenu adminMenu = new AdminMenu();
    adminMenu.setId(id);
    adminMenu.setStatus(-1);

    return menuMpService.updateById(adminMenu);
  }

  @Override
  public Page<AdminMenu> list(Integer parentId, Integer pageSize, Integer pageNum) {
    Page<AdminMenu> page = new Page<>();
    page.setCurrent(pageNum);
    page.setSize(pageSize);
    QueryWrapper<AdminMenu> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("parent_id", parentId);
    queryWrapper.eq("status", 1);
    queryWrapper.orderByDesc("sort");
    return menuMpService.page(page, queryWrapper);
  }

  @Override
  public List<AdminMenuNode> treeList() {
    QueryWrapper<AdminMenu> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("status", 1);
    queryWrapper.orderByDesc("sort");
    List<AdminMenu> menuList = menuMpService.list(queryWrapper);
    List<AdminMenuNode> result =
        menuList.stream()
            .filter(menu -> menu.getParentId().equals(0))
            .map(menu -> convertMenuNode(menu, menuList))
            .collect(Collectors.toList());
    return result;
  }

  @Override
  public boolean updateHidden(Integer id, Integer hidden) {
    AdminMenu adminMenu = new AdminMenu();
    adminMenu.setId(id);
    adminMenu.setHidden(hidden);
    return menuMpService.updateById(adminMenu);
  }

  /** 将AdminMenu转化为AdminMenuNode并设置children属性 */
  private AdminMenuNode convertMenuNode(AdminMenu menu, List<AdminMenu> menuList) {
    AdminMenuNode node = new AdminMenuNode();
    BeanUtils.copyProperties(menu, node);
    List<AdminMenuNode> children =
        menuList.stream()
            .filter(subMenu -> subMenu.getParentId().equals(menu.getId()))
            .map(subMenu -> convertMenuNode(subMenu, menuList))
            .collect(Collectors.toList());
    node.setChildren(children);
    return node;
  }
}
