package org.gamerbot.support;

public interface CommonSupport {
    String SPLIT_ID = "=:";

    default String getOrDefault(String value, String defaultValue) {
        return value.equalsIgnoreCase("<NONE>") ? defaultValue : value;
    }
}
