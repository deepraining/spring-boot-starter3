package dr.sbs.mp.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 微信用户
 * </p>
 *
 * @author deepraining
 * @since 
 */
@Getter
@Setter
@TableName("wx_user")
@ApiModel(value = "WxUser对象", description = "微信用户")
public class WxUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键Id(分布式生成Id)")
    private Long id;

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("性别（1：男，2：女，0：未知）")
    private Byte gender;

    @ApiModelProperty("生日")
    private LocalDate birthday;

    @ApiModelProperty("省")
    private String province;

    @ApiModelProperty("市")
    private String city;

    @ApiModelProperty("区")
    private String district;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("注册日期")
    private LocalDateTime registerDate;

    @ApiModelProperty("最后使用日期")
    private LocalDateTime lastLogin;

    @ApiModelProperty("微信unionId")
    private String unionId;

    @ApiModelProperty("小程序openId")
    private String miniOpenId;

    @ApiModelProperty("公众号openId")
    private String mpOpenId;

    @ApiModelProperty("状态：-1 删除、0 禁用、1 启用")
    private Byte status;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;
}
