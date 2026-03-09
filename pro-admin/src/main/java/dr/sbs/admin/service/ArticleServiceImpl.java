package dr.sbs.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import dr.sbs.admin.dao.ArticleDao;
import dr.sbs.admin.dto.ArticleCreateParam;
import dr.sbs.admin.dto.ArticleRecord;
import dr.sbs.common.util.SbsIdUtil;
import dr.sbs.mp.entity.Article;
import dr.sbs.mp.service.ArticleMpService;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** 后台文章管理Service实现类 */
@Service
public class ArticleServiceImpl implements ArticleService {
  @Autowired private ArticleMpService articleMpService;
  @Autowired private ArticleDao articleDao;

  @Override
  public Page<ArticleRecord> list(String searchKey, Integer pageSize, Integer pageNum) {
    int limit = pageSize;
    int offset = (pageNum - 1) * pageSize;

    List<ArticleRecord> list = articleDao.getList(limit, offset, searchKey);
    Integer total = articleDao.getListTotal(searchKey);

    Page<ArticleRecord> page = new Page<>();
    page.setCurrent(pageNum);
    page.setSize(pageSize);
    page.setTotal(total);
    page.setRecords(list);
    return page;
  }

  @Override
  public boolean create(ArticleCreateParam articleCreateParam) {
    Article article = new Article();
    BeanUtils.copyProperties(articleCreateParam, article);
    article.setId(SbsIdUtil.nextId());
    article.setFrontUserId(0L);
    return articleMpService.save(article);
  }

  @Override
  public boolean update(Long id, ArticleCreateParam articleCreateParam) {
    Article article = new Article();
    BeanUtils.copyProperties(articleCreateParam, article);
    article.setId(id);
    return articleMpService.updateById(article);
  }

  @Override
  public boolean delete(Long id) {
    Article article = new Article();
    article.setId(id);
    article.setStatus((byte) 0);
    return articleMpService.updateById(article);
  }
}
