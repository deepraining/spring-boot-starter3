package dr.sbs.admin.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import dr.sbs.admin.dao.AdminRoleDao;
import dr.sbs.mp.entity.AdminMenu;
import dr.sbs.mp.entity.AdminResource;
import dr.sbs.mp.entity.AdminRole;
import dr.sbs.mp.entity.AdminRoleMenuRelation;
import dr.sbs.mp.entity.AdminRoleResourceRelation;
import dr.sbs.mp.service.AdminRoleMenuRelationMpService;
import dr.sbs.mp.service.AdminRoleMpService;
import dr.sbs.mp.service.AdminRoleResourceRelationMpService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/** 后台角色管理Service实现类 */
@Service
public class AdminRoleServiceImpl implements AdminRoleService {
  @Autowired private AdminRoleMpService roleMpService;
  @Autowired private AdminRoleMenuRelationMpService roleMenuRelationMpService;
  @Autowired private AdminRoleResourceRelationMpService roleResourceRelationMpService;
  @Autowired private AdminRoleDao roleDao;
  @Autowired private AdminUserCacheService userCacheService;

  @Override
  public boolean create(AdminRole role) {
    role.setSort(0);
    return roleMpService.save(role);
  }

  @Override
  public boolean update(Integer id, AdminRole role) {
    role.setId(id);
    return roleMpService.updateById(role);
  }

  @Override
  public boolean delete(List<Integer> ids) {
    QueryWrapper<AdminRole> queryWrapper = new QueryWrapper<>();
    queryWrapper.in("id", ids);
    AdminRole adminRole = new AdminRole();
    adminRole.setStatus(-1);
    boolean result = roleMpService.update(adminRole, queryWrapper);
    userCacheService.delResourceListByRoleIds(ids);
    return result;
  }

  @Override
  public List<AdminRole> list() {
    QueryWrapper<AdminRole> queryWrapper = new QueryWrapper<>();
    queryWrapper.ne("status", -1);
    return roleMpService.list(queryWrapper);
  }

  @Override
  public Page<AdminRole> list(String keyword, Integer pageSize, Integer pageNum) {
    Page<AdminRole> page = new Page<>();
    page.setCurrent(pageNum);
    page.setSize(pageSize);
    QueryWrapper<AdminRole> queryWrapper = new QueryWrapper<>();
    queryWrapper.ne("status", -1);
    if (!StringUtils.isEmpty(keyword)) {
      queryWrapper.like("name", keyword);
    }
    return roleMpService.page(page, queryWrapper);
  }

  @Override
  public List<AdminMenu> listMenu(Integer roleId) {
    return roleDao.getMenuList(roleId);
  }

  @Override
  public List<AdminResource> listResource(Integer roleId) {
    return roleDao.getResourceList(roleId);
  }

  @Override
  public boolean allocMenu(Integer roleId, List<Integer> menuIds) {
    // 先删除原有关系
    QueryWrapper<AdminRoleMenuRelation> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("role_id", roleId);
    AdminRoleMenuRelation adminRoleMenuRelation = new AdminRoleMenuRelation();
    adminRoleMenuRelation.setStatus(-1);
    roleMenuRelationMpService.update(adminRoleMenuRelation, queryWrapper);
    // 批量插入新关系
    for (Integer menuId : menuIds) {
      AdminRoleMenuRelation relation = new AdminRoleMenuRelation();
      relation.setRoleId(roleId);
      relation.setMenuId(menuId);
      roleMenuRelationMpService.save(relation);
    }
    return true;
  }

  @Override
  public boolean allocResource(Integer roleId, List<Integer> resourceIds) {
    // 先删除原有关系
    QueryWrapper<AdminRoleResourceRelation> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("role_id", roleId);
    AdminRoleResourceRelation adminRoleResourceRelation = new AdminRoleResourceRelation();
    adminRoleResourceRelation.setStatus(-1);
    roleResourceRelationMpService.update(adminRoleResourceRelation, queryWrapper);
    // 批量插入新关系
    for (Integer resourceId : resourceIds) {
      AdminRoleResourceRelation relation = new AdminRoleResourceRelation();
      relation.setRoleId(roleId);
      relation.setResourceId(resourceId);
      roleResourceRelationMpService.save(relation);
    }
    userCacheService.delResourceListByRole(roleId);
    return true;
  }
}
