package dr.sbs.front.controller;

import dr.sbs.common.CommonResult;
import dr.sbs.front.dto.UpdatePasswordParam;
import dr.sbs.front.dto.UserCreateParam;
import dr.sbs.front.service.UserService;
import dr.sbs.front.util.ResultFilter;
import dr.sbs.mp.entity.FrontUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "AccountController", description = "Sign up, Login, Password management")
@RequestMapping("/api/account")
public class AccountController {
  @Autowired private UserService userService;

  @Operation(summary = "Sign up")
  @RequestMapping(value = "/register", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult<FrontUser> register(
      @RequestBody @Validated UserCreateParam userCreateParam, BindingResult bindingResult) {
    FrontUser user = userService.register(userCreateParam);
    if (user != null) {
      return CommonResult.success(ResultFilter.filterFrontUser(user));
    }
    return CommonResult.failed();
  }

  @Operation(summary = "Update password")
  @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult<Integer> updatePassword(
      @RequestBody @Validated UpdatePasswordParam updatePasswordParam,
      BindingResult bindingResult) {
    boolean result = userService.updatePassword(updatePasswordParam);
    if (result) return CommonResult.success(1);
    return CommonResult.failed();
  }

  @Operation(summary = "Current user information")
  @RequestMapping(value = "/currentUser", method = RequestMethod.GET)
  @ResponseBody
  public CommonResult<FrontUser> currentUser() {
    FrontUser user = userService.getCurrentUser();
    if (user != null) return CommonResult.success(ResultFilter.filterFrontUser(user));
    return CommonResult.failed();
  }
}
