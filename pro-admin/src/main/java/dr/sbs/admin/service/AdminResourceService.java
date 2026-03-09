package dr.sbs.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import dr.sbs.mp.entity.AdminResource;
import java.util.List;

/** 后台资源管理Service */
public interface AdminResourceService {
  /** 添加资源 */
  boolean create(AdminResource adminResource);

  /** 修改资源 */
  boolean update(Integer id, AdminResource adminResource);

  /** 获取资源详情 */
  AdminResource getItem(Integer id);

  /** 删除资源 */
  boolean delete(Integer id);

  /** 分页查询资源 */
  Page<AdminResource> list(
      Integer categoryId, String nameKeyword, String urlKeyword, Integer pageSize, Integer pageNum);

  /** 查询全部资源 */
  List<AdminResource> listAll();
}
