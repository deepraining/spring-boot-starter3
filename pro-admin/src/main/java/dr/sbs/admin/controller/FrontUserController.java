package dr.sbs.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import dr.sbs.admin.dto.FrontUserCreateParam;
import dr.sbs.admin.service.FrontUserService;
import dr.sbs.common.CommonPage;
import dr.sbs.common.CommonResult;
import dr.sbs.mp.entity.FrontUser;
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

/** 前端用户管理 */
@Controller
@Tag(name = "FrontUserController", description = "前端用户管理")
@RequestMapping("/frontUser")
public class FrontUserController {
  @Autowired private FrontUserService frontUserService;

  @Operation(summary = "查询前端用户列表")
  @RequestMapping(value = "/list", method = RequestMethod.GET)
  @ResponseBody
  public CommonResult<CommonPage<FrontUser>> list(
      @RequestParam(value = "pageSize", defaultValue = "10") @Parameter(description = "每页条数")
          Integer pageSize,
      @RequestParam(value = "pageNum", defaultValue = "1") @Parameter(description = "页码")
          Integer pageNum,
      @RequestParam(value = "searchKey", defaultValue = "") @Parameter(description = "搜索关键字")
          String searchKey) {
    Page<FrontUser> frontUserList = frontUserService.list(searchKey, pageSize, pageNum);
    return CommonResult.success(CommonPage.toPage(frontUserList));
  }

  @Operation(summary = "添加前端用户")
  @RequestMapping(value = "/create", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult<Integer> create(
      @RequestBody @Validated FrontUserCreateParam frontUserCreateParam,
      BindingResult bindingResult) {
    boolean result = frontUserService.create(frontUserCreateParam);
    if (result) {
      return CommonResult.success(1);
    } else {
      return CommonResult.failed();
    }
  }

  @Operation(summary = "修改前端用户")
  @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult<Integer> update(
      @PathVariable Long id,
      @RequestBody @Validated FrontUserCreateParam frontUserCreateParam,
      BindingResult bindingResult) {
    boolean result = frontUserService.update(id, frontUserCreateParam);
    if (result) {
      return CommonResult.success(1);
    } else {
      return CommonResult.failed();
    }
  }

  @Operation(summary = "根据ID删除前端用户")
  @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult<Integer> delete(@PathVariable Long id) {
    boolean result = frontUserService.delete(id);
    if (result) {
      return CommonResult.success(1);
    } else {
      return CommonResult.failed();
    }
  }
}
