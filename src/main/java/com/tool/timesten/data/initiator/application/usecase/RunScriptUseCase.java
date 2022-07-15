package com.tool.timesten.data.initiator.application.usecase;

import com.tool.timesten.data.initiator.domain.entity.SqlScriptSupplier;

public interface RunScriptUseCase {
    void run(SqlScriptSupplier scriptSupplier);
}
