package dr.sbs.front.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import dr.sbs.common.CommonPage;
import dr.sbs.common.CommonResult;
import dr.sbs.front.service.ArticleService;
import dr.sbs.mp.entity.Article;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "UserController", description = "User management")
@RequestMapping("/api/user")
public class UserController {
  @Autowired private ArticleService articleService;

  @Operation(summary = "article list")
  @RequestMapping(value = "/articles", method = RequestMethod.GET)
  @ResponseBody
  public CommonResult<CommonPage<Article>> articles(
      @RequestParam(value = "pageSize", defaultValue = "10")
          @Parameter(description = "每页条数")
          Integer pageSize,
      @RequestParam(value = "pageNum", defaultValue = "1")
          @Parameter(description = "页码")
          Integer pageNum) {
    Page<Article> list = articleService.myList(pageSize, pageNum);
    return CommonResult.success(CommonPage.toPage(list));
  }
}
