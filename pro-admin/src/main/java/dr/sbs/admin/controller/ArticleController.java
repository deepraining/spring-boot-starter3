package dr.sbs.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import dr.sbs.admin.dto.ArticleCreateParam;
import dr.sbs.admin.dto.ArticleRecord;
import dr.sbs.admin.service.ArticleService;
import dr.sbs.common.CommonPage;
import dr.sbs.common.CommonResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/** 文章管理 */
@Controller
@Tag(name = "ArticleController", description = "文章管理")
@RequestMapping("/article")
public class ArticleController {
  @Autowired private ArticleService articleService;

  @Operation(summary = "查询文章列表")
  @RequestMapping(value = "/list", method = RequestMethod.GET)
  @ResponseBody
  public CommonResult<CommonPage<ArticleRecord>> list(
      @RequestParam(value = "pageSize", defaultValue = "10")
          @Parameter(description = "每页条数")
          Integer pageSize,
      @RequestParam(value = "pageNum", defaultValue = "1")
      @Parameter(description = "页码")
          Integer pageNum,
      @RequestParam(value = "searchKey", defaultValue = "")
          @Parameter(description = "搜索关键字")
          String searchKey) {
    Page<ArticleRecord> articleList = articleService.list(searchKey, pageSize, pageNum);
    return CommonResult.success(CommonPage.toPage(articleList));
  }

  @Operation(summary = "添加文章")
  @RequestMapping(value = "/create", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult<Integer> create(
      @RequestBody @Validated ArticleCreateParam articleCreateParam, BindingResult bindingResult) {
    boolean result = articleService.create(articleCreateParam);
    if (result) {
      return CommonResult.success(1);
    } else {
      return CommonResult.failed();
    }
  }

  @Operation(summary = "修改文章")
  @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult<Integer> update(
      @PathVariable Long id,
      @RequestBody @Validated ArticleCreateParam articleCreateParam,
      BindingResult bindingResult) {
    boolean result = articleService.update(id, articleCreateParam);
    if (result) {
      return CommonResult.success(1);
    } else {
      return CommonResult.failed();
    }
  }

  @Operation(summary = "根据ID删除文章")
  @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult<Integer> delete(@PathVariable Long id) {
    boolean result = articleService.delete(id);
    if (result) {
      return CommonResult.success(1);
    } else {
      return CommonResult.failed();
    }
  }
}
