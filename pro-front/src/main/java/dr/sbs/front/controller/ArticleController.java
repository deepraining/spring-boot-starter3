package dr.sbs.front.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import dr.sbs.common.CommonPage;
import dr.sbs.common.CommonResult;
import dr.sbs.front.dto.ArticleCreateParam;
import dr.sbs.front.service.ArticleService;
import dr.sbs.front.service.UserService;
import dr.sbs.mp.entity.Article;
import dr.sbs.mp.entity.FrontUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "ArticleController", description = "Article management")
@RequestMapping("/api/article")
public class ArticleController {
  @Autowired private ArticleService articleService;
  @Autowired private UserService userService;

  @Operation(summary = "Create article")
  @RequestMapping(value = "/create", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult<Integer> create(
      @RequestBody @Validated ArticleCreateParam articleCreateParam, BindingResult bindingResult) {
    FrontUser user = userService.getCurrentUser();
    if (user == null) {
      return CommonResult.unauthorized("Not Logged-in");
    }

    boolean result = articleService.create(articleCreateParam);
    if (result) {
      return CommonResult.success(1);
    }
    return CommonResult.failed();
  }

  @Operation(summary = "Update article")
  @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult<Integer> update(
      @PathVariable Long id,
      @RequestBody @Validated ArticleCreateParam articleCreateParam,
      BindingResult bindingResult) {
    FrontUser user = userService.getCurrentUser();
    if (user == null) {
      return CommonResult.unauthorized("Not logged in");
    }

    Article article = articleService.getItem(id);
    if (!article.getFrontUserId().equals(user.getId())) {
      return CommonResult.forbidden("No privileges");
    }

    boolean result = articleService.update(id, articleCreateParam);
    if (result) {
      return CommonResult.success(1);
    }
    return CommonResult.failed();
  }

  @Operation(summary = "Delete article")
  @RequestMapping(value = "/delete", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult<Integer> delete(@RequestParam Long id) {
    FrontUser user = userService.getCurrentUser();
    if (user == null) {
      return CommonResult.unauthorized("Not logged in");
    }

    Article article = articleService.getItem(id);
    if (!article.getFrontUserId().equals(user.getId())) {
      return CommonResult.forbidden("No privileges");
    }

    boolean result = articleService.delete(id);
    if (result) {
      return CommonResult.success(1);
    }
    return CommonResult.failed();
  }

  @Operation(summary = "Query list")
  @RequestMapping(value = "/list", method = RequestMethod.GET)
  @ResponseBody
  public CommonResult<CommonPage<Article>> list(
      @RequestParam(value = "pageSize", defaultValue = "10")
          @Parameter(description = "每页条数")
          Integer pageSize,
      @RequestParam(value = "pageNum", defaultValue = "1")
          @Parameter(description = "页码")
          Integer pageNum,
      @RequestParam(value = "searchKey", defaultValue = "")
          @Parameter(description = "搜索关键字")
          String searchKey) {
    Page<Article> queryList = articleService.list(searchKey, pageSize, pageNum);
    return CommonResult.success(CommonPage.toPage(queryList));
  }

  @Operation(summary = "Get a record")
  @RequestMapping(value = "/record/{id}", method = RequestMethod.GET)
  @ResponseBody
  public CommonResult<Article> record(@PathVariable long id) {
    Article article = articleService.getItem(id);
    return CommonResult.success(article);
  }
}
