package com.tool.timesten.data.initiator.framework.adapter.config.holder;

import com.tool.timesten.data.initiator.domain.entity.upsert.MrxAccountsUpsert;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class MrxAccountsUpsertHolder implements SetupScriptBeanHolder<String, MrxAccountsUpsert>, InitializingBean {
    private final Map<String, MrxAccountsUpsert> repo = new HashMap<>();
    private final List<MrxAccountsUpsert> allAccountUpsertBeans;

    @Override
    public MrxAccountsUpsert get(String key) {
        return repo.get(key);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        allAccountUpsertBeans
        .forEach(e -> {
            repo.put(e.getAcctno(), e);
        });
    }
}
