package dr.sbs.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

/** 前端用户创建参数 */
@Getter
@Setter
public class FrontUserCreateParam {
  // position从10开始，每次增加10，预防中间插入留置
  @Schema(description = "用户名", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotEmpty(message = "用户名不能为空")
  private String username;

  @Schema(description = "电子邮箱", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotEmpty(message = "电子邮箱不能为空")
  private String email;
}
