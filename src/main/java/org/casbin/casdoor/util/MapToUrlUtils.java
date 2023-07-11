package org.casbin.casdoor.util;

import org.casbin.casdoor.config.CasdoorConfig;

import java.util.Map;
import java.util.stream.Collectors;

public class MapToUrlUtils {

    public static String mapToUrlParams(Map<String, String> map) {
        return map.entrySet()
                .stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining("&"));
    }

    public static void queryMapInit(int p, int pageSize, Map<String, String> queryMap, CasdoorConfig casdoorConfig) {
        queryMap.put("owner", casdoorConfig.getOrganizationName());
        queryMap.put("clientId", casdoorConfig.getClientId());
        queryMap.put("clientSecret",casdoorConfig.getClientSecret());
        queryMap.put("p", Integer.toString(p));
        queryMap.put("pageSize", Integer.toString(pageSize));
    }

}