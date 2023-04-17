package com.mow.config;

import com.mow.properties.PayPalProperties;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class PayPalConfig {

    @Autowired
    PayPalProperties payPalProperties;

    @Bean
    public OAuthTokenCredential oAuthTokenCredential() {
        return new OAuthTokenCredential(
                payPalProperties.getClientId(),
                payPalProperties.getClientSecret(),
                Map.of("mode", payPalProperties.getMode())
        );
    }

    @Bean
    public APIContext apiContext() throws PayPalRESTException {
        APIContext apiContext = new APIContext(oAuthTokenCredential().getAccessToken());
        apiContext.setConfigurationMap(Map.of("mode", payPalProperties.getMode()));
        return apiContext;
    }

}
