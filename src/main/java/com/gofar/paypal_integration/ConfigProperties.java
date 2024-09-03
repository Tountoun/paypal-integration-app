package com.gofar.paypal_integration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "paypal")
@Data
@Component
public class ConfigProperties {

    private String clientId;
    private String clientSecret;
    private String mode;
}
