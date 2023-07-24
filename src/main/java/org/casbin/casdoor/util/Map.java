package org.casbin.casdoor.util;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.stream.Collectors;

public class Map {

    public static String mapToUrlParams(@Nullable java.util.Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return "";
        }
        return map.entrySet()
                .stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining("&"));
    }

    @NotNull
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
    @NotNull
    public static <T> java.util.Map<T, T> of(@NotNull T... kv) {
        java.util.Map<T, T> map = new HashMap<>(kv.length / 2 + 1);
        for (int i = 0; i < kv.length; i += 2) {
            map.put(kv[i], kv[i + 1]);
        }
        return map;
    }

    public static <T, V> java.util.Map<T, V> of(@NotNull T k1, @NotNull V v1, @NotNull T k2, @NotNull V v2) {
        java.util.Map<T, V> map = new HashMap<>(2);
        map.put(k1, v1);
        map.put(k2, v2);
        return map;
    }
}