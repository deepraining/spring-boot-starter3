package dr.sbs.front.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import dr.sbs.common.exception.ApiAssert;
import dr.sbs.common.util.SbsIdUtil;
import dr.sbs.front.dto.ArticleCreateParam;
import dr.sbs.mp2.entity.FrontUser;
import dr.sbs.mp3.entity.Article;
import dr.sbs.mp3.service.ArticleMpService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ArticleServiceImpl implements ArticleService {
  @Autowired private ArticleMpService articleMpService;
  @Autowired private UserService userService;

  @Override
  public Page<Article> list(String searchKey, Integer pageSize, Integer pageNum) {
    Page<Article> page = new Page<>();
    page.setCurrent(pageNum);
    page.setSize(pageSize);
    QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("status", 1);
    queryWrapper.orderByDesc("id");

    if (!StringUtils.isEmpty(searchKey)) {
      queryWrapper.like("title", searchKey);
    }
    return articleMpService.page(page, queryWrapper);
  }

  @Override
  public Page<Article> myList(Integer pageSize, Integer pageNum) {
    FrontUser user = userService.getCurrentUser();
    Page<Article> page = new Page<>();
    page.setCurrent(pageNum);
    page.setSize(pageSize);

    if (user == null) return page;

    QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("status", 1);
    queryWrapper.eq("front_user_id", user.getId());
    queryWrapper.orderByDesc("id");
    return articleMpService.page(page, queryWrapper);
  }

  @Override
  public boolean create(ArticleCreateParam articleCreateParam) {
    FrontUser user = userService.getCurrentUser();
    if (user == null) ApiAssert.fail("未登录");

    Article article = new Article();
    BeanUtils.copyProperties(articleCreateParam, article);
    article.setId(SbsIdUtil.nextId());
    article.setFrontUserId(user.getId());
    return articleMpService.save(article);
  }

  @Override
  public boolean update(Long id, ArticleCreateParam articleCreateParam) {
    FrontUser user = userService.getCurrentUser();
    if (user == null) ApiAssert.fail("未登录");

    Article oldArticle = articleMpService.getById(id);

    if (oldArticle == null) ApiAssert.fail("文章不存在");
    if (!oldArticle.getFrontUserId().equals(user.getId())) ApiAssert.fail("无权限");

    Article article = new Article();
    BeanUtils.copyProperties(articleCreateParam, article);
    article.setId(id);

    return articleMpService.updateById(article);
  }

  @Override
  public boolean delete(Long id) {
    FrontUser user = userService.getCurrentUser();
    if (user == null) ApiAssert.fail("未登录");

    Article oldArticle = articleMpService.getById(id);

    if (oldArticle == null) ApiAssert.fail("文章不存在");
    if (!oldArticle.getFrontUserId().equals(user.getId())) ApiAssert.fail("无权限");

    Article article = new Article();
    article.setId(id);
    article.setStatus((byte) 0);

    return articleMpService.updateById(article);
  }

  @Override
  public Article getItem(Long id) {
    return articleMpService.getById(id);
  }
}
