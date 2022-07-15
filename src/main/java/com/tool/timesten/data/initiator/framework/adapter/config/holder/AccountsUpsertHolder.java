package com.tool.timesten.data.initiator.framework.adapter.config.holder;

import com.tool.timesten.data.initiator.domain.entity.upsert.AccountsUpsert;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AccountsUpsertHolder implements SetupScriptBeanHolder<String, AccountsUpsert>, InitializingBean {
    private final Map<String, AccountsUpsert> repo = new HashMap<>();
    private final List<AccountsUpsert> allAccountUpsertBeans;

    @Override
    public AccountsUpsert get(String key) {
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
