package dr.sbs.mp.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 文章
 * </p>
 *
 * @author deepraining
 * @since 
 */
@Getter
@Setter
@Schema(name = "Article", description = "文章")
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "简介")
    private String intro;

    @Schema(description = "内容")
    private String content;

    @Schema(description = "创建者 front_user id")
    private Long frontUserId;

    @Schema(description = "阅读数")
    private Integer readCount;

    @Schema(description = "点赞数")
    private Integer supportCount;

    @Schema(description = "状态：-1 删除、0 禁用、1 启用")
    private Byte status;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
