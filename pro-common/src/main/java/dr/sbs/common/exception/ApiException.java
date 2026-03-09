package dr.sbs.common.exception;

import dr.sbs.common.CommonResult;
import dr.sbs.common.IErrorCode;

// 自定义API异常
public class ApiException extends RuntimeException {
  private IErrorCode errorCode;
  private CommonResult commonResult;

  public ApiException(IErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }

  public ApiException(CommonResult commonResult) {
    super(commonResult.getMessage());
    this.commonResult = commonResult;
  }

  public ApiException(String message) {
    super(message);
  }

  public ApiException(Throwable cause) {
    super(cause);
  }

  public ApiException(String message, Throwable cause) {
    super(message, cause);
  }

  public IErrorCode getErrorCode() {
    return errorCode;
  }

  public CommonResult getCommonResult() {
    return commonResult;
  }
}
