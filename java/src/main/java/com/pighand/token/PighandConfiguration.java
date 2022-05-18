package com.pighand.token;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author shuli495
 */
@ComponentScan("com.pighand.token")
@Configuration
@ConfigurationProperties(prefix = PighandConfiguration.PIGHAND_PREFIX)
public class PighandConfiguration {
    public static final String PIGHAND_PREFIX = "pighand";

    public static PighandTokenProperties token;

    public PighandTokenProperties getToken() {
        return token;
    }

    public void setToken(PighandTokenProperties token) {
        PighandConfiguration.token = token;
    }
}
