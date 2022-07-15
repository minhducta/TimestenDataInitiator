package com.tool.timesten.data.initiator.framework.adapter.config.holder;

import com.tool.timesten.data.initiator.domain.entity.upsert.MrxBasketUpsert;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class MrxBasketUpsertHolder implements SetupScriptBeanHolder<MrxBasketUpsertHolder.Key, MrxBasketUpsert>, InitializingBean {
    private final Map<Key, MrxBasketUpsert> repo = new HashMap<>();
    private final List<MrxBasketUpsert> allAccountUpsertBeans;

    @Override
    public MrxBasketUpsert get(Key key) {
        return repo.get(key);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        allAccountUpsertBeans
        .forEach(e -> {
            repo.put(new Key(e.getBasketid(), e.getSymbol()), e);
        });
    }

    @Value
    public static class Key {
        String basketid;
        String symbol;
    }
}
