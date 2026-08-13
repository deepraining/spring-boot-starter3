package dr.sbs.admin.controller;

import dr.sbs.admin.service.AdminResourceCategoryService;
import dr.sbs.common.CommonResult;
import dr.sbs.mp.entity.AdminResourceCategory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/** 后台资源分类管理 */
@RestController
@Tag(name = "AdminResourceCategoryController", description = "后台资源分类管理")
@RequestMapping("/adminResourceCategory")
public class AdminResourceCategoryController {
  @Autowired private AdminResourceCategoryService resourceCategoryService;

  @Operation(summary = "查询所有后台资源分类")
  @RequestMapping(value = "/listAll", method = RequestMethod.GET)
  public CommonResult<List<AdminResourceCategory>> listAll() {
    List<AdminResourceCategory> resourceList = resourceCategoryService.listAll();
    return CommonResult.success(resourceList);
  }

  @Operation(summary = "添加后台资源分类")
  @RequestMapping(value = "/create", method = RequestMethod.POST)
  public CommonResult<Integer> create(
      @RequestBody @Validated AdminResourceCategory adminResourceCategory) {
    boolean result = resourceCategoryService.create(adminResourceCategory);
    if (result) {
      return CommonResult.success(1);
    } else {
      return CommonResult.failed();
    }
  }

  @Operation(summary = "修改后台资源分类")
  @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
  public CommonResult<Integer> update(
      @PathVariable Integer id,
      @RequestBody @Validated AdminResourceCategory adminResourceCategory) {
    boolean result = resourceCategoryService.update(id, adminResourceCategory);
    if (result) {
      return CommonResult.success(1);
    } else {
      return CommonResult.failed();
    }
  }

  @Operation(summary = "根据ID删除后台资源")
  @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
  public CommonResult<Integer> delete(@PathVariable Integer id) {
    boolean result = resourceCategoryService.delete(id);
    if (result) {
      return CommonResult.success(1);
    } else {
      return CommonResult.failed();
    }
  }
}
