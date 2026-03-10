package dr.sbs.mp.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(name = "WxPayTrans", description = "微信支付流水")
public class WxPayTrans implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "支付流水号（微信支付回调的 transaction_id 字段）")
    @TableId("trans_id")
    private String transId;

    @Schema(description = "业务单据号（微信支付回调的 out_trade_no 字段）")
    private String billNo;

    @Schema(description = "支付用户openId（微信支付回调的 openid 字段）")
    private String openId;

    @Schema(description = "收款商户号（微信支付回调的 mch_id 字段）")
    private String mchId;

    @Schema(description = "应用的appId（微信支付回调的 appid 字段）")
    private String appId;

    @Schema(description = "订单金额（微信支付回调的 total_fee/100 字段）")
    private BigDecimal totalFee;

    @Schema(description = "支付金额（微信支付回调的 cash_fee/100 字段）")
    private BigDecimal cashFee;

    @Schema(description = "优惠券支付金额（微信支付回调的 coupon_fee/100 字段）")
    private BigDecimal couponFee;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
