package com.tool.timesten.data.initiator.framework.adapter.config.holder;

import com.tool.timesten.data.initiator.domain.entity.upsert.MrxSlpManagementUpsert;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class MrxSlpManagementUpsertHolder implements SetupScriptBeanHolder<MrxSlpManagementUpsertHolder.Key, MrxSlpManagementUpsert>, InitializingBean {
    private final Map<Key, MrxSlpManagementUpsert> repo = new HashMap<>();
    private final List<MrxSlpManagementUpsert> allAccountUpsertBeans;

    @Override
    public MrxSlpManagementUpsert get(Key key) {
        return repo.get(key);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        allAccountUpsertBeans
        .forEach(e -> {
            repo.put(new Key(e.getSlpid(), e.getSymbol()), e);
        });
    }

    @Value
    public static class Key {
        String slpid;
        String symbol;
    }
}
