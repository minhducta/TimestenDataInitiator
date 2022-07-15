package com.tool.timesten.data.initiator.application.port.out;

import com.tool.timesten.data.initiator.domain.entity.SqlScriptSupplier;

public interface DatabaseAccessOutputPort {
    void runScript(SqlScriptSupplier scriptSupplier);
}
