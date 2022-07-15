package com.tool.timesten.data.initiator.domain.entity.upsert;

import com.tool.timesten.data.initiator.domain.entity.annotation.PrimaryKey;
import com.tool.timesten.data.initiator.domain.entity.annotation.TableName;
import lombok.*;

@Builder
@Setter
@Getter
@ToString
@TableName("mrx_slp_info")
@With
public class MrxSlpInfoUpsert extends AbstractUpsertScriptSupplier {
    @PrimaryKey
    String slpid;
    String level;
    String type;
    String slpid_mass;
    String created_time;
}