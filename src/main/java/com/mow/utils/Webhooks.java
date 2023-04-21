package com.mow.utils;

import com.mow.properties.DiscordProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
public class Webhooks {

    @Autowired
    JSONBuilder jsonBuilder;

    @Autowired
    DiscordProperties discordProperties;

    public ResponseEntity<?> getWebhook() {
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl = String.format("%s/%s/%s", discordProperties.getUrl(), discordProperties.getClientId(), discordProperties.getToken());
        return restTemplate.getForEntity(fooResourceUrl, String.class);
    }

    public void postWebhook(String message) {
        this.send(message);
    }

    private void send(String message) {
        String url = String.format("%s/%s/%s", "https://discord.com/api/webhooks/", "1095188870552891474", "3QS3nYOKVT4tYY46d602N56UKf3dZmhUS9dAAPe5gxu_JOBEeJZXI-YhmeQ162VJl7Pb");
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        JSONBuilder json = new JSONBuilder();
        json.put("content", message);

        HttpEntity<String> httpEntity = new HttpEntity<>(json.stringify(), httpHeaders);
        restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
    }

}
