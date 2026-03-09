package dr.sbs.mp.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 微信支付流水
 * </p>
 *
 * @author deepraining
 * @since 
 */
@Getter
@Setter
@TableName("wx_pay_trans")
@ApiModel(value = "WxPayTrans对象", description = "微信支付流水")
public class WxPayTrans implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("支付流水号（微信支付回调的 transaction_id 字段）")
    @TableId("trans_id")
    private String transId;

    @ApiModelProperty("业务单据号（微信支付回调的 out_trade_no 字段）")
    private String billNo;

    @ApiModelProperty("支付用户openId（微信支付回调的 openid 字段）")
    private String openId;

    @ApiModelProperty("收款商户号（微信支付回调的 mch_id 字段）")
    private String mchId;

    @ApiModelProperty("应用的appId（微信支付回调的 appid 字段）")
    private String appId;

    @ApiModelProperty("订单金额（微信支付回调的 total_fee/100 字段）")
    private BigDecimal totalFee;

    @ApiModelProperty("支付金额（微信支付回调的 cash_fee/100 字段）")
    private BigDecimal cashFee;

    @ApiModelProperty("优惠券支付金额（微信支付回调的 coupon_fee/100 字段）")
    private BigDecimal couponFee;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;
}
