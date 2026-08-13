package dr.sbs.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import dr.sbs.admin.dto.AdminMenuNode;
import dr.sbs.admin.service.AdminMenuService;
import dr.sbs.common.CommonPage;
import dr.sbs.common.CommonResult;
import dr.sbs.mp.entity.AdminMenu;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** 后台菜单管理 */
@RestController
@Tag(name = "AdminMenuController", description = "后台菜单管理")
@RequestMapping("/adminMenu")
public class AdminMenuController {
  @Autowired private AdminMenuService menuService;

  @Operation(summary = "添加后台菜单")
  @RequestMapping(value = "/create", method = RequestMethod.POST)
  public CommonResult<Integer> create(@RequestBody @Validated AdminMenu adminMenu) {
    boolean result = menuService.create(adminMenu);
    if (result) {
      return CommonResult.success(1);
    } else {
      return CommonResult.failed();
    }
  }

  @Operation(summary = "修改后台菜单")
  @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
  public CommonResult<Integer> update(
      @PathVariable Integer id, @RequestBody @Validated AdminMenu adminMenu) {
    boolean result = menuService.update(id, adminMenu);
    if (result) {
      return CommonResult.success(1);
    } else {
      return CommonResult.failed();
    }
  }

  @Operation(summary = "根据ID获取菜单详情")
  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public CommonResult<AdminMenu> getItem(@PathVariable Integer id) {
    AdminMenu adminMenu = menuService.getItem(id);
    return CommonResult.success(adminMenu);
  }

  @Operation(summary = "根据ID删除后台菜单")
  @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
  public CommonResult<Integer> delete(@PathVariable Integer id) {
    boolean result = menuService.delete(id);
    if (result) {
      return CommonResult.success(1);
    } else {
      return CommonResult.failed();
    }
  }

  @Operation(summary = "分页查询后台菜单")
  @RequestMapping(value = "/list/{parentId}", method = RequestMethod.GET)
  public CommonResult<CommonPage<AdminMenu>> list(
      @PathVariable Integer parentId,
      @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
      @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
    Page<AdminMenu> menuList = menuService.list(parentId, pageSize, pageNum);
    return CommonResult.success(CommonPage.toPage(menuList));
  }

  @Operation(summary = "树形结构返回所有菜单列表")
  @RequestMapping(value = "/treeList", method = RequestMethod.GET)
  public CommonResult<List<AdminMenuNode>> treeList() {
    List<AdminMenuNode> list = menuService.treeList();
    return CommonResult.success(list);
  }

  @Operation(summary = "修改菜单显示状态")
  @RequestMapping(value = "/updateHidden/{id}", method = RequestMethod.POST)
  public CommonResult<Integer> updateHidden(
      @PathVariable Integer id, @RequestParam("hidden") Integer hidden) {
    boolean result = menuService.updateHidden(id, hidden);
    if (result) {
      return CommonResult.success(1);
    } else {
      return CommonResult.failed();
    }
  }
}
