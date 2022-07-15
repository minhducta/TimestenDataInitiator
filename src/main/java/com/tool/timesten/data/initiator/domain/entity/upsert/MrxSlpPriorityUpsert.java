package com.tool.timesten.data.initiator.domain.entity.upsert;

import com.tool.timesten.data.initiator.domain.entity.annotation.PrimaryKey;
import com.tool.timesten.data.initiator.domain.entity.annotation.TableName;
import lombok.*;

@Builder
@Setter
@Getter
@ToString
@TableName("mrx_slp_priority")
@With
public class MrxSlpPriorityUpsert extends AbstractUpsertScriptSupplier {
    @PrimaryKey
    String symbol;
    @PrimaryKey
    String side;
    String priority;
}