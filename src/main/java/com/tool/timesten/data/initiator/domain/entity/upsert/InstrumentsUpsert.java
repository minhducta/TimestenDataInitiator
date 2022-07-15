package com.tool.timesten.data.initiator.domain.entity.upsert;

import com.tool.timesten.data.initiator.domain.entity.annotation.PrimaryKey;
import com.tool.timesten.data.initiator.domain.entity.annotation.TableName;
import lombok.*;

@Builder
@Setter
@Getter
@ToString
@TableName("instruments")
@With
public class InstrumentsUpsert extends AbstractUpsertScriptSupplier {
    @PrimaryKey
    String symbol;
    String symbolnum;
    String fullname;
    String cficode;
    String exchange;
    String board;
    String price_ce;
    String price_fl;
    String price_rf;
    String qttysum;
    String fqtty;
    String halt;
    String secstatus;
}
