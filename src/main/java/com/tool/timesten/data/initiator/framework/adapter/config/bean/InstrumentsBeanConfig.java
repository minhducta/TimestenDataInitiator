package com.tool.timesten.data.initiator.framework.adapter.config.bean;

import com.tool.timesten.data.initiator.domain.entity.upsert.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InstrumentsBeanConfig {
    @Bean
    InstrumentsUpsert defaultInstrument() {
        return InstrumentsUpsert.builder()
            .symbol("TCB")
            .symbolnum("32")
            .fullname("ductm test symbol")
            .cficode("ES")
            .exchange("HSX")
            .board("HSX")
            .price_ce("22750")
            .price_fl("19850")
            .price_rf("21300")
            .qttysum("754171070")
            .fqtty("0")
            .halt("N")
            .secstatus("17")
            .build();
    }
}
