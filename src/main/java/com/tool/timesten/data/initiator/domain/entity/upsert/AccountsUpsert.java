package com.tool.timesten.data.initiator.domain.entity.upsert;

import com.tool.timesten.data.initiator.domain.entity.annotation.PrimaryKey;
import com.tool.timesten.data.initiator.domain.entity.annotation.TableName;
import lombok.*;

@Builder
@Setter
@Getter
@ToString
@TableName("accounts")
@With
public class AccountsUpsert extends AbstractUpsertScriptSupplier {
    @PrimaryKey
    String acctno;
    String actype;
    String custodycd;
    String policycd;
    String grname;
    String acclass;
    String formulacd;
    String poolid;
    String roomid;
    String basketid;
    String basketid_ub;
    String status;
    String rate_brk_s;
    String rate_brk_b;
    String rate_tax;
    String rate_adv;
    String rate_ub;
    String ratio_init;
    String ratio_main;
    String ratio_exec;
    String trfbuyext;
    String trfbuyamt;
    String banklink;
    String bankacctno;
    String bankcode;
    String bod_nav;
    String bod_seamt;
    String bod_seass;
    String bod_adv;
    String bod_debt;
    String bod_debt_m;
    String bod_debt_t0;
    String bod_d_margin;
    String bod_d_margin_ub;
    String bod_td;
    String bod_balance;
    String bod_intbal;
    String bod_intacr;
    String bod_payable;
    String bod_crlimit;
    String bod_t0value;
    String bod_rcasht0;
    String bod_rcasht1;
    String bod_rcasht2;
    String bod_rcasht3;
    String bod_rcashtn;
    String bod_scasht0;
    String bod_scasht1;
    String bod_scasht2;
    String bod_scasht3;
    String bod_scashtn;
    String blocked_advbal;
    String calc_ratio;
    String calc_advbal;
    String calc_sellmort;
    String calc_avlbal;
    String calc_pp0;
    String calc_nav;
    String calc_asset;
    String calc_odramt;
    String calc_trfbuyamt;
    String calc_trfbuy;
    String bankavlbal;
    String activests;
    String dueoveramt;
    String depofeeamt;
    String cidepofeeacr;
    String mortagefee;
    String inday_adv;
    String cash_dividend;
    String asbalance;
    String emkamt;
    String mblock;
    String holdbalance;
    String iscopyia;
    String subactype;
    String lastchange;
    String margin_principal_debt;
    String margin_interest;
    String bankavlbf;
    String ad_used;
}