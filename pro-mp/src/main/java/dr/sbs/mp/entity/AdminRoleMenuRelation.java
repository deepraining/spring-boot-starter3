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
 * 后台角色菜单关系
 * </p>
 *
 * @author deepraining
 * @since 
 */
@Getter
@Setter
@TableName("admin_role_menu_relation")
@Schema(name = "AdminRoleMenuRelation", description = "后台角色菜单关系")
public class AdminRoleMenuRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "角色ID")
    private Integer roleId;

    @Schema(description = "菜单ID")
    private Integer menuId;

    @Schema(description = "状态：-1 删除、1 启用")
    private Integer status;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
