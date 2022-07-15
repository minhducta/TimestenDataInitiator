package com.tool.timesten.data.initiator.domain.entity.upsert;

import com.tool.timesten.data.initiator.domain.entity.annotation.PrimaryKey;
import com.tool.timesten.data.initiator.domain.entity.annotation.TableName;
import lombok.*;

@Builder
@Setter
@Getter
@ToString
@TableName("orders")
@With
public class OrdersUpsert extends AbstractUpsertScriptSupplier {
    @PrimaryKey
    String orderid;
    String txdate;
    String norb;
    String sessionex;
    String quoteid;
    String confirmid;
    String userid;
    String custodycd;
    String acctno;
    String symbol;
    String reforderid;
    String rootorderid;
    String originorderid;
    String dealid;
    String flagorder;
    String side;
    String subside;
    String status;
    String substatus;
    String time_created;
    String time_send;
    String typecd;
    String subtypecd;
    String rate_adv;
    String rate_brk;
    String rate_tax;
    String rate_buy;
    String price_margin;
    String price_asset;
    String quote_price;
    String quote_qtty;
    String exec_amt;
    String exec_qtty;
    String remain_qtty;
    String cancel_qtty;
    String admend_qtty;
    String mort_qtty;
    String marked;
    String priority;
    String lastchange;
    String isdisposal;
    String bondorderid;
    String clientcode;
    String delta_qtty;
}