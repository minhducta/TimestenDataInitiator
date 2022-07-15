package com.tool.timesten.data.initiator.domain.entity.upsert;

import com.tool.timesten.data.initiator.domain.entity.annotation.PrimaryKey;
import com.tool.timesten.data.initiator.domain.entity.annotation.TableName;
import lombok.*;

@Builder
@Setter
@Getter
@ToString
@TableName("mrx_accounts")
@With
public class MrxAccountsUpsert extends AbstractUpsertScriptSupplier {
    @PrimaryKey
    String acctno;
    String type;
    String poolid_mr;
    String poolid_adv;
    String slpid;
    String roomid;
    String basketid;
    String crlimit;
    String rate_ri;
    String rate_rm;
    String rate_rl;
    String od_due;
    String od_overdue;
    String od_prin;
    String od_int;
    String ad_used;
    String int_over;
    String lastchange;
}