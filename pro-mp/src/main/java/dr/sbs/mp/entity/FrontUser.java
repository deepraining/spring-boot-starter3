package dr.sbs.mp.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 前端用户
 * </p>
 *
 * @author deepraining
 * @since 
 */
@Getter
@Setter
@TableName("front_user")
@Schema(name = "FrontUser", description = "前端用户")
public class FrontUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "电子邮箱")
    private String email;

    @Schema(description = "密码")
    private String password;

    @Schema(description = "状态：-1 删除、0 禁用、1 启用")
    private Byte status;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
