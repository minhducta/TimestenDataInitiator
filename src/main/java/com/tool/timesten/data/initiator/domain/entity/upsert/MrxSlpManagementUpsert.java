package com.tool.timesten.data.initiator.domain.entity.upsert;

import com.tool.timesten.data.initiator.domain.entity.annotation.PrimaryKey;
import com.tool.timesten.data.initiator.domain.entity.annotation.TableName;
import lombok.*;

@Builder
@Setter
@Getter
@ToString
@TableName("mrx_slp_management")
@With
public class MrxSlpManagementUpsert extends AbstractUpsertScriptSupplier {
    @PrimaryKey
    String slpid;
    @PrimaryKey
    String symbol;
    String parentid;
    String type;
    String granted;
    String inused;
    String remain;
    String level;
    String last_allocation_id;
    String used_inday;
    String is_slpmass;
    String ki;
}
