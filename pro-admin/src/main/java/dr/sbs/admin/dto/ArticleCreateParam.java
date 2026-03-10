package dr.sbs.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

/** 文章创建参数 */
@Getter
@Setter
public class ArticleCreateParam {
  // position从10开始，每次增加10，预防中间插入留置
  @Schema(description = "标题", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotEmpty(message = "标题不能为空")
  private String title;

  @Schema(description = "简介", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotEmpty(message = "简介不能为空")
  private String intro;

  @Schema(description = "内容", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotEmpty(message = "内容不能为空")
  private String content;
}
