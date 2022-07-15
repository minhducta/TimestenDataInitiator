package com.tool.timesten.data.initiator.domain.entity.upsert;

import com.tool.timesten.data.initiator.domain.entity.annotation.PrimaryKey;
import com.tool.timesten.data.initiator.domain.entity.annotation.TableName;
import lombok.*;

@Builder
@Setter
@Getter
@ToString
@TableName("mrx_basket")
@With
public class MrxBasketUpsert extends AbstractUpsertScriptSupplier {
    @PrimaryKey
    String basketid;
    @PrimaryKey
    String symbol;
    String rate_asset;
    String rate_margin;
    String price_asset;
    String price_margin;
    String ra_right;
    String rp_right;
    String p_right;
    String ra_divbonusreg;
    String rp_divbonusreg;
    String pa_divbonusreg_max;
    String pp_divbonusreg_max;
    String ra_cashdiv;
    String rp_cashdiv;
    String lastchange;
}
