package com.tool.timesten.data.initiator.framework.adapter.out;

import com.tool.timesten.data.initiator.application.port.out.DatabaseAccessOutputPort;
import com.tool.timesten.data.initiator.domain.entity.SqlScriptSupplier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
@Component
public class DatabaseAccessOutputAdapter implements DatabaseAccessOutputPort {
    private final JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public void runScript(SqlScriptSupplier scriptSupplier) {
        String script = scriptSupplier.generateUpsertStatement();
        log.info("run script: {}", script);
        jdbcTemplate.execute(script);
    }
}
