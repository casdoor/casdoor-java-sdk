package org.casbin.casdoor.util;

import java.io.Serializable;
import java.util.Map;

public class QueryUtils {
    public static String buildQuery(Map<String, Serializable> params) {
        if (params == null || params.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Serializable> entry : params.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        return sb.substring(0, sb.length() - 1);
    }
}
