package com.mow;

import com.mow.properties.DiscordProperties;
import com.mow.utils.JSONBuilder;
import com.mow.utils.Webhook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

@AutoConfigureMockMvc
@SpringBootTest
public class WebhookTests {

    @Autowired
    DiscordProperties discordProperties;

    @Autowired
    JSONBuilder jsonBuilder;

    @Test
    void getWebhook() {
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl = String.format("%s/%s/%s", discordProperties.getUrl(), discordProperties.getClientId(), discordProperties.getToken());
        ResponseEntity<Object> response = restTemplate.getForEntity(fooResourceUrl, Object.class);

        System.out.println(response.getBody());
    }

    @Test
    void postWebhook() {
        String url = String.format("%s/%s/%s", discordProperties.getUrl(), discordProperties.getClientId(), discordProperties.getToken());
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        String json = jsonBuilder
                .put("content", "Hi everyone! <@1041202503343149146>")
                .stringify();
        HttpEntity<String> httpEntity = new HttpEntity<>(json, httpHeaders);

        restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
    }

}
