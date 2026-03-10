package dr.sbs.mp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 后台资源分类
 * </p>
 *
 * @author deepraining
 * @since 
 */
@Getter
@Setter
@TableName("admin_resource_category")
@Schema(name = "AdminResourceCategory", description = "后台资源分类")
public class AdminResourceCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "分类名称")
    private String name;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "状态：-1 删除、1 启用")
    private Integer status;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
