package org.casbin.casdoor.util;


import java.util.Map;
import java.util.stream.Collectors;

public class MapToUrlUtils {

    public static String mapToUrlParams(Map<String, String> map) {
        return map.entrySet()
                .stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining("&"));
    }

}