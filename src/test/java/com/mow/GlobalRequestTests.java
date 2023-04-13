package com.mow;

import com.mow.request.GlobalRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class GlobalRequestTests {

    private GlobalRequest globalRequest = new GlobalRequest();

    @BeforeEach
    void setup() {
        globalRequest.setObjects("token", "topSecretToken2023");
    }

    @Test
    void getValue() {
        globalRequest.get("token");
        assertNotNull(globalRequest);
    }

}
