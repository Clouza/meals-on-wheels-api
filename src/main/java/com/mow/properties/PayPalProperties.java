package com.mow.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "paypal")
public class PayPalProperties {

    private String mode;
    private String clientId;
    private String clientSecret;

}
