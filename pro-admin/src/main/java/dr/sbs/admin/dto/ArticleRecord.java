package dr.sbs.admin.dto;

import dr.sbs.mp.entity.Article;
import dr.sbs.mp.entity.FrontUser;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleRecord extends Article {
  // position从1000开始，每次增加10，预防中间插入留置
  @Schema(description = "前端用户")
  private FrontUser frontUser;
}
