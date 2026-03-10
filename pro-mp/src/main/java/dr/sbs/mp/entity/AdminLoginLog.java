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
 * 后台用户登录日志
 * </p>
 *
 * @author deepraining
 * @since 
 */
@Getter
@Setter
@TableName("admin_login_log")
@Schema(name = "AdminLoginLog", description = "后台用户登录日志")
public class AdminLoginLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer userId;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "用户昵称")
    private String nickname;

    @Schema(description = "ip地址")
    private String ip;

    @Schema(description = "浏览器登录类型")
    private String userAgent;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
