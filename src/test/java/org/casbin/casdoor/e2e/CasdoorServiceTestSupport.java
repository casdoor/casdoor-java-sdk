package org.casbin.casdoor.e2e;

import org.casbin.casdoor.config.CasdoorConfig;

public abstract class CasdoorServiceTestSupport {
    protected CasdoorConfig casdoorConfig = new CasdoorConfig(
            "http://localhost:8000",
            "108a9909661cc7631d01",
            "373b406c9e277861da901a3754689e979ac98526",
            "CasdoorSecret",
            "built-in",
            "app-built-in"
    );
}
