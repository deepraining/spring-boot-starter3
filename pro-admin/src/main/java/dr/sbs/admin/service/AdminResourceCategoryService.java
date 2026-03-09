package dr.sbs.admin.service;

import dr.sbs.mp.entity.AdminResourceCategory;
import java.util.List;

/** 后台资源分类管理Service */
public interface AdminResourceCategoryService {

  /** 获取所有资源分类 */
  List<AdminResourceCategory> listAll();

  /** 创建资源分类 */
  boolean create(AdminResourceCategory adminResourceCategory);

  /** 修改资源分类 */
  boolean update(Integer id, AdminResourceCategory adminResourceCategory);

  /** 删除资源分类 */
  boolean delete(Integer id);
}
