package com.mow.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "webhook.discord")
public class DiscordProperties {

    private String url;
    private String clientId;
    private String token;

}
