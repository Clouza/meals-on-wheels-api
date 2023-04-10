package com.mow;

import com.mow.utils.JSONBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureMockMvc
@SpringBootTest
public class UtilityTests {

    @Autowired
    JSONBuilder jsonBuilder;

    @Test
    void JsonBuilder() {
        JSONBuilder jsonBuilder = new JSONBuilder();
        jsonBuilder.put("response", "Ok");
        assertEquals(jsonBuilder.stringify(), "{\"response\":\"Ok\"}");
    }

    @Test
    void JsonBuilderBean() {
        jsonBuilder.put("response", "Ok");
        assertEquals(jsonBuilder.stringify(), "{\"response\":\"Ok\"}");
    }

}
