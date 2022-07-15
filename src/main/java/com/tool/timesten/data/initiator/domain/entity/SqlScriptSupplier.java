package com.tool.timesten.data.initiator.domain.entity;

import java.util.function.Supplier;

public interface SqlScriptSupplier {
    String generateUpsertStatement();
}
