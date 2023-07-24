package org.casbin.casdoor.support;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.casbin.casdoor.CasdoorClient;

import okhttp3.logging.HttpLoggingInterceptor.Level;

public class CasdoorClientProvider {
    private CasdoorClientProvider() {
    }

    public static CasdoorClient get() {
        return new CasdoorClient.Builder()
                .endpoint("http://localhost:8000")
                .baseUri("api")
                .clientId("6368a1041b46e5d156ab")
                .clientSecret("09764d62a75a5b644c449ba42c44062e14224ba5")
                .certificate(getCertificate())
                .organizationName("built-in")
                .applicationName("app-built-in")
                .log(Level.BODY)
                .build();
    }

    private static String getCertificate() {
        InputStream inputStream = CasdoorClientProvider.class.getResourceAsStream("/certificate.pem");
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
