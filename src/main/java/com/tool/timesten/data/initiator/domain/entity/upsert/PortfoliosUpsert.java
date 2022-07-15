package com.tool.timesten.data.initiator.domain.entity.upsert;

import com.tool.timesten.data.initiator.domain.entity.annotation.PrimaryKey;
import com.tool.timesten.data.initiator.domain.entity.annotation.TableName;
import lombok.*;

@Builder
@Setter
@Getter
@ToString
@TableName("portfolios")
@With
public class PortfoliosUpsert extends AbstractUpsertScriptSupplier {
    @PrimaryKey
    String acctno;
    @PrimaryKey
    String symbol;
    String trade;
    String mortgage;
    String receiving;
    String buyingqtty;
    String sellingqtty;
    String sellingqttymort;
    String assetqtty;
    String marked;
    String markedcom;
    String avgprice;
    String bod_rt0;
    String bod_rt1;
    String bod_rt2;
    String bod_rt3;
    String bod_rtn;
    String bod_st0;
    String bod_st1;
    String bod_st2;
    String bod_st3;
    String bod_stn;
    String lastchange;
    String roomid;
    String msell;
    String mcustomer;
    String sellingqtty_fso;
    String ca_trade;
    String ca_tax;
    String exercisedca;
    String stockdividend;
    String unexercisedca;
    String waitfortrade;
    String waitfortransfer;
    String waitforwithdraw;
    String prevqtty;
    String cash_div_amt;
    String dmarked;
}
