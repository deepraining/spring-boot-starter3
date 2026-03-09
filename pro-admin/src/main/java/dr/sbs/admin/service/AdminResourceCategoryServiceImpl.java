package dr.sbs.admin.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import dr.sbs.mp.entity.AdminResourceCategory;
import dr.sbs.mp.service.AdminResourceCategoryMpService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** 后台资源分类管理Service实现类 */
@Service
public class AdminResourceCategoryServiceImpl implements AdminResourceCategoryService {
  @Autowired private AdminResourceCategoryMpService resourceCategoryMpService;

  @Override
  public List<AdminResourceCategory> listAll() {
    QueryWrapper<AdminResourceCategory> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("status", 1);
    queryWrapper.orderByDesc("sort");
    return resourceCategoryMpService.list(queryWrapper);
  }

  @Override
  public boolean create(AdminResourceCategory adminResourceCategory) {
    return resourceCategoryMpService.save(adminResourceCategory);
  }

  @Override
  public boolean update(Integer id, AdminResourceCategory adminResourceCategory) {
    adminResourceCategory.setId(id);
    return resourceCategoryMpService.updateById(adminResourceCategory);
  }

  @Override
  public boolean delete(Integer id) {
    AdminResourceCategory adminResourceCategory = new AdminResourceCategory();
    adminResourceCategory.setId(id);
    adminResourceCategory.setStatus(-1);
    return resourceCategoryMpService.updateById(adminResourceCategory);
  }
}
