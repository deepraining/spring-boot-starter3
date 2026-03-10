package dr.sbs.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

/** 用户登录参数 */
@Getter
@Setter
public class AdminUserParam {
  @Schema(description = "用户名", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotEmpty(message = "用户名不能为空")
  private String username;

  @Schema(description = "密码", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotEmpty(message = "密码不能为空")
  private String password;

  @Schema(description = "用户头像")
  private String avatar;

  @Schema(description = "邮箱")
  @Email(message = "邮箱格式不合法")
  private String email;

  @Schema(description = "用户昵称")
  private String nickname;

  @Schema(description = "备注")
  private String note;
}
