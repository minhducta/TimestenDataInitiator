package com.tool.timesten.data.initiator.application.port.in;

import com.tool.timesten.data.initiator.application.port.out.DatabaseAccessOutputPort;
import com.tool.timesten.data.initiator.application.usecase.RunScriptUseCase;
import com.tool.timesten.data.initiator.domain.entity.SqlScriptSupplier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class RunScriptInputPort implements RunScriptUseCase {
    private final DatabaseAccessOutputPort databaseAccessOutputPort;

    @Override
    public void run(SqlScriptSupplier scriptSupplier) {
        log.info("Prepare running: {}", scriptSupplier);
        databaseAccessOutputPort.runScript(scriptSupplier);
        log.info("Done running: {}", scriptSupplier);
    }
}
