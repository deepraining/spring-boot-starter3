package dr.sbs.mp.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "FrontUser对象", description = "前端用户")
public class FrontUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("电子邮箱")
    private String email;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("状态：-1 删除、0 禁用、1 启用")
    private Byte status;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;
}
