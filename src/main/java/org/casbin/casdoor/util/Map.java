package org.casbin.casdoor.util;


import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.HashMap;
import java.util.stream.Collectors;

public class Map {

    public static String mapToUrlParams(@Nullable java.util.Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return "";
        }
        return map.entrySet()
                .stream()
                .filter(entry -> entry.getValue() != null)
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining("&"));
    }

    @Nonnull
    public static <T, V> java.util.Map<T, V> mergeMap(@Nullable java.util.Map<T, V> map1, @Nullable java.util.Map<T, V> map2) {
        if (map1 == null) {
            return map2 == null ? new HashMap<>() : map2;
        }
        if (map2 == null) {
            return map1;
        }
        map1.putAll(map2);
        return map1;
    }

    @SafeVarargs
    @Nonnull
    public static <T> java.util.Map<T, T> of(@Nonnull T... kv) {
        java.util.Map<T, T> map = new HashMap<>(kv.length / 2 + 1);
        for (int i = 0; i < kv.length; i += 2) {
            map.put(kv[i], kv[i + 1]);
        }
        return map;
    }

    public static <T, V> java.util.Map<T, V> of(@Nonnull T k1, @Nonnull V v1, @Nonnull T k2, @Nonnull V v2) {
        java.util.Map<T, V> map = new HashMap<>(2);
        map.put(k1, v1);
        map.put(k2, v2);
        return map;
    }
}