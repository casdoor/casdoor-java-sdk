package org.casbin.casdoor.support;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.casbin.casdoor.config.CasdoorConfig;

public class ConfigFactory {
    private ConfigFactory() {
    }

    /**
     * You should replace config and certificate.pem content with your own Casdoor instance.
     */
    public static CasdoorConfig getConfig() {
        return new CasdoorConfig(
                "http://localhost:8000",
                "6368a1041b46e5d156ab",
                "09764d62a75a5b644c449ba42c44062e14224ba5",
                getCertificate(),
                "built-in",
                "app-built-in");
    }

    private static String getCertificate() {
        InputStream inputStream = ConfigFactory.class.getResourceAsStream("/certificate.pem");
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return resultStringBuilder.toString().trim();
    }
}
