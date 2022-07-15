package com.tool.timesten.data.initiator.framework.adapter.config.holder;

import com.tool.timesten.data.initiator.domain.entity.upsert.PortfoliosUpsert;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class PortfoliosUpsertHolder implements SetupScriptBeanHolder<String, List<PortfoliosUpsert>>, InitializingBean {
    private final Map<String, List<PortfoliosUpsert>> repo = new HashMap<>();
    private final List<PortfoliosUpsert> allBeans;

    @Override
    public List<PortfoliosUpsert> get(String key) {
        return repo.getOrDefault(key, Collections.emptyList());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("allBeans: {}", allBeans);
        allBeans
            .forEach(e -> {
                List<PortfoliosUpsert> list = repo.computeIfAbsent(e.getAcctno(), k -> new LinkedList<>());
                list.add(e);
            });
    }
}
