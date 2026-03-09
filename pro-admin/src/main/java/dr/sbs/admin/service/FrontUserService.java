package dr.sbs.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import dr.sbs.admin.dto.FrontUserCreateParam;
import dr.sbs.mp.entity.FrontUser;

/** 前端用户管理Service */
public interface FrontUserService {
  /** 获取前端用户列表 */
  Page<FrontUser> list(String searchKey, Integer pageSize, Integer pageNum);

  /** 创建前端用户 */
  boolean create(FrontUserCreateParam frontUserCreateParam);

  /** 修改前端用户 */
  boolean update(Long id, FrontUserCreateParam frontUserCreateParam);

  /** 删除前端用户 */
  boolean delete(Long id);
}
