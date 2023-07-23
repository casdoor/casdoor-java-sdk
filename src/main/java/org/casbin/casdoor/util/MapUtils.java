package org.casbin.casdoor.util;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.stream.Collectors;

public class MapUtils {

    public static String mapToUrlParams(@Nullable Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return "";
        }
        return map.entrySet()
                .stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining("&"));
    }

    @NotNull
    public static <T, V> Map<T, V> mergeMap(@Nullable Map<T, V> map1, @Nullable Map<T, V> map2) {
        if (map1 == null) {
            return map2 == null ? Map.of() : map2;
        }
        if (map2 == null) {
            return map1;
        }
        map1.putAll(map2);
        return map1;
    }
}