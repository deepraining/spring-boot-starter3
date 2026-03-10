package dr.sbs.mp.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(name = "WxUser", description = "微信用户")
public class WxUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键Id(分布式生成Id)")
    private Long id;

    @Schema(description = "昵称")
    private String nickname;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "性别（1：男，2：女，0：未知）")
    private Byte gender;

    @Schema(description = "生日")
    private LocalDate birthday;

    @Schema(description = "省")
    private String province;

    @Schema(description = "市")
    private String city;

    @Schema(description = "区")
    private String district;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "注册日期")
    private LocalDateTime registerDate;

    @Schema(description = "最后使用日期")
    private LocalDateTime lastLogin;

    @Schema(description = "微信unionId")
    private String unionId;

    @Schema(description = "小程序openId")
    private String miniOpenId;

    @Schema(description = "公众号openId")
    private String mpOpenId;

    @Schema(description = "状态：-1 删除、0 禁用、1 启用")
    private Byte status;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
