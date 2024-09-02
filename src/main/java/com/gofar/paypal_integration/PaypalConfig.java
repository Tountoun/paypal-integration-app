package com.gofar.paypal_integration;

import com.paypal.base.rest.APIContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaypalConfig {

    private ConfigProperties properties;

    @Bean
    public APIContext apiContext() {
        return new APIContext(
                properties.getClientId(),
                properties.getClientSecret(),
                properties.getMode()
        );
    }

    @Autowired
    public void setProperties(ConfigProperties properties) {
        this.properties = properties;
    }
}
