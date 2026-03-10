package dr.sbs.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

/** 修改用户名密码参数 */
@Getter
@Setter
public class AdminUpdatePasswordParam {
  @Schema(description = "用户名", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotEmpty(message = "用户名不能为空")
  private String username;

  @Schema(description = "旧密码", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotEmpty(message = "旧密码不能为空")
  private String oldPassword;

  @Schema(description = "新密码", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotEmpty(message = "新密码不能为空")
  private String newPassword;
}
