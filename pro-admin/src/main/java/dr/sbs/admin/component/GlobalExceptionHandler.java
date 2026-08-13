package dr.sbs.admin.component;

import dr.sbs.common.CommonResult;
import dr.sbs.common.exception.ApiException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

// 全局异常处理
// 注：把这个文件复制到每项目的主包名下，才能生效
// 或者启动时设置 `@SpringBootApplication(scanBasePackages = {"dr.sbs"})`
@ControllerAdvice
public class GlobalExceptionHandler {
  @ResponseBody
  @ExceptionHandler(value = ApiException.class)
  public CommonResult handle(ApiException e) {
    if (e.getErrorCode() != null) {
      return CommonResult.failed(e.getErrorCode());
    }
    return CommonResult.failed(e.getMessage());
  }

  @ResponseBody
  @ExceptionHandler(value = MethodArgumentNotValidException.class)
  public CommonResult handle(MethodArgumentNotValidException e) {
    // 只展示第一个信息
    List<String> errorTexts = new ArrayList<>();
    e.getBindingResult()
        .getFieldErrors()
        .forEach(error -> errorTexts.add(error.getDefaultMessage()));
    return CommonResult.failed(errorTexts.size() > 0 ? errorTexts.get(0) : "参数有误");
  }
}
