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
 * 后台用户
 * </p>
 *
 * @author deepraining
 * @since 
 */
@Getter
@Setter
@TableName("admin_user")
@Schema(name = "AdminUser", description = "后台用户")
public class AdminUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "加密密码")
    private String password;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "昵称")
    private String nickname;

    @Schema(description = "备注信息")
    private String note;

    @Schema(description = "最后登录时间")
    private LocalDateTime lastLoginTime;

    @Schema(description = "状态：-1 删除、0 禁用、1 启用")
    private Integer status;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
