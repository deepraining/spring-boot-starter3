package dr.sbs.front.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import dr.sbs.front.dto.ArticleCreateParam;
import dr.sbs.mp.entity.Article;

public interface ArticleService {
  Page<Article> list(String searchKey, Integer pageSize, Integer pageNum);

  Page<Article> myList(Integer pageSize, Integer pageNum);

  boolean create(ArticleCreateParam articleCreateParam);

  boolean update(Long id, ArticleCreateParam articleCreateParam);

  boolean delete(Long id);

  Article getItem(Long id);
}
