package dr.sbs.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import dr.sbs.admin.dto.ArticleCreateParam;
import dr.sbs.admin.dto.ArticleRecord;

/** 文章管理Service */
public interface ArticleService {
  /** 获取文章列表 */
  Page<ArticleRecord> list(String searchKey, Integer pageSize, Integer pageNum);

  /** 创建文章 */
  boolean create(ArticleCreateParam articleCreateParam);

  /** 修改文章 */
  boolean update(Long id, ArticleCreateParam articleCreateParam);

  /** 删除文章 */
  boolean delete(Long id);
}
