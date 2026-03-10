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
 * 后台用户角色
 * </p>
 *
 * @author deepraining
 * @since 
 */
@Getter
@Setter
@TableName("admin_role")
@Schema(name = "AdminRole", description = "后台用户角色")
public class AdminRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "状态：状态：-1 删除、0 禁用、1 启用")
    private Integer status;

    @Schema(description = "排序值")
    private Integer sort;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
