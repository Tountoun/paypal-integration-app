package com.gofar.paypal_integration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "paypal")
@Data
public class ConfigProperties {

    private String clientId;
    private String clientSecret;
    private String mode;
}
