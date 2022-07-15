package com.tool.timesten.data.initiator.framework.adapter.config.bean;

import com.tool.timesten.data.initiator.application.port.in.RunScriptInputPort;
import com.tool.timesten.data.initiator.application.port.out.DatabaseAccessOutputPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    RunScriptInputPort runScriptInputPort(DatabaseAccessOutputPort databaseAccessOutputPort) {
        return new RunScriptInputPort(databaseAccessOutputPort);
    }
}
