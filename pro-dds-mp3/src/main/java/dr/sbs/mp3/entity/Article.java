package dr.sbs.mp3.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "Article对象", description = "文章")
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("简介")
    private String intro;

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("创建者 front_user id")
    private Long frontUserId;

    @ApiModelProperty("阅读数")
    private Integer readCount;

    @ApiModelProperty("点赞数")
    private Integer supportCount;

    @ApiModelProperty("状态：-1 删除、0 禁用、1 启用")
    private Byte status;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;
}
