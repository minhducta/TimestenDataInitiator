package com.tool.timesten.data.initiator.domain.entity.upsert;

import com.tool.timesten.data.initiator.domain.entity.SqlScriptSupplier;
import com.tool.timesten.data.initiator.domain.entity.annotation.PrimaryKey;
import com.tool.timesten.data.initiator.domain.entity.annotation.TableName;
import org.apache.commons.text.StringSubstitutor;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

public abstract class AbstractUpsertScriptSupplier implements SqlScriptSupplier {
    public String generateUpsertStatement() {
        return new StringSubstitutor(getSubstitutors()).replace(getUpsertTemplate());
    }

    private String getTableName() {
        return this.getClass().getAnnotation(TableName.class).value();
    }

    private Set<String> getPrimaryFields() {
        return Arrays.stream(this.getClass().getDeclaredFields())
            .peek(field -> field.setAccessible(true))
            .filter(field -> field.isAnnotationPresent(PrimaryKey.class))
            .map(Field::getName)
            .collect(Collectors.toSet());
    }

    private List<Map.Entry<String, String>> genKeyValuePair() {
        return List.copyOf(Objects.requireNonNull(asMap()).entrySet());
    }

    private Map<String, String> getSubstitutors() {
        return Map.of(
            "whereConditionByPk", generateWhereConditionByPk(),
            "updateStatement", generateUpdateStatement(),
            "insertStatement", generateInsertStatement(),
            "tableName", getTableName()
        );
    }

    private String getUpsertTemplate() {
        return "" +
            "declare\n" +
            "    l_count number;\n" +
            "begin\n" +
            "    select count(1) into l_count from ${tableName} where ${whereConditionByPk};\n" +
            "    if l_count > 0 then\n" +
            "        ${updateStatement} where ${whereConditionByPk};\n" +
            "    else\n" +
            "        ${insertStatement};\n" +
            "    end if;\n" +
            "end;";
    }

    private String generateWhereConditionByPk() {
        Set<String> pks = getPrimaryFields();
        Map<String, String> map = asMap();
        return pks.stream()
            .map(k -> String.format("%s = '%s'", k, map.get(k)))
            .collect(Collectors.joining(" and "));
    }

    private String generateInsertStatement() {
        String template = "insert into %s (%s) values (%s) ";
        List<Map.Entry<String, String>> keyValues = genKeyValuePair();

        keyValues = keyValues.stream()
            .filter(e -> !e.getValue().isEmpty()).toList();

        var keys = keyValues.stream().map(Map.Entry::getKey).toList();
        var values = keyValues.stream().map(Map.Entry::getValue).map(v -> String.format("'%s'", v)).toList();
        return String.format(template, getTableName(), String.join(", ", keys), String.join(", ", values));
    }

    private String generateUpdateStatement() {
        String template = "update %s set %s";
        List<String> keyValues = genKeyValuePair()
            .stream()
            .filter(e -> !e.getValue().isEmpty())
            .filter(e -> !"acctno".equals(e.getKey()))
            .filter(e -> !"symbol".equals(e.getKey()))
            .map(e -> String.format("%s = '%s'", e.getKey(), e.getValue())).toList();
        return String.format(template, getTableName(), String.join(", ", keyValues));
    }

    protected final Map<String, String> asMap() {
        try {
            var fields = this.getClass().getDeclaredFields();
            Map<String, String> map = new HashMap<>();
            for (var field : fields) {
                field.setAccessible(true);
                if (Modifier.isStatic(field.getModifiers()))
                    continue;
                String key = field.getName();
                Object value = field.get(this);
                map.put(key, String.valueOf(Objects.requireNonNullElse(value, "")));
            }
            return Collections.unmodifiableMap(map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
}
