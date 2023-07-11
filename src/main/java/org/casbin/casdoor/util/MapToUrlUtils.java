package org.casbin.casdoor.util;

import java.util.Map;

public class MapToUrlUtils {

    public static String mapToUrlParams(Map<String, String> map) {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            result.append(entry.getKey());
            result.append("=");
            result.append(entry.getValue());
            result.append("&");
        }
        return result.length() > 0 ? result.substring(0, result.length() - 1) : result.toString();
    }
}