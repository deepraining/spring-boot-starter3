package dr.sbs.admin.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import dr.sbs.mp.entity.AdminResource;
import dr.sbs.mp.service.AdminResourceMpService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** 后台资源管理Service实现类 */
@Service
public class AdminResourceServiceImpl implements AdminResourceService {
  @Autowired private AdminResourceMpService resourceMpService;
  @Autowired private AdminUserCacheService userCacheService;

  @Override
  public boolean create(AdminResource adminResource) {
    return resourceMpService.save(adminResource);
  }

  @Override
  public boolean update(Integer id, AdminResource adminResource) {
    adminResource.setId(id);
    boolean result = resourceMpService.updateById(adminResource);
    userCacheService.delResourceListByResource(id);
    return result;
  }

  @Override
  public AdminResource getItem(Integer id) {
    return resourceMpService.getById(id);
  }

  @Override
  public boolean delete(Integer id) {
    AdminResource adminResource = new AdminResource();
    adminResource.setId(id);
    adminResource.setStatus(-1);
    boolean result = resourceMpService.updateById(adminResource);
    userCacheService.delResourceListByResource(id);
    return result;
  }

  @Override
  public Page<AdminResource> list(
      Integer categoryId, String nameKeyword, String urlKeyword, Integer pageSize, Integer pageNum) {
    Page<AdminResource> page = new Page<>();
    page.setCurrent(pageNum);
    page.setSize(pageSize);
    QueryWrapper<AdminResource> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("status", 1);
    if (categoryId != null) {
      queryWrapper.eq("category_id", categoryId);
    }
    if (StrUtil.isNotEmpty(nameKeyword)) {
      queryWrapper.like("name", nameKeyword);
    }
    if (StrUtil.isNotEmpty(urlKeyword)) {
      queryWrapper.like("url", urlKeyword);
    }
    return resourceMpService.page(page, queryWrapper);
  }

  @Override
  public List<AdminResource> listAll() {
    QueryWrapper<AdminResource> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("status", 1);
    return resourceMpService.list(queryWrapper);
  }
}
