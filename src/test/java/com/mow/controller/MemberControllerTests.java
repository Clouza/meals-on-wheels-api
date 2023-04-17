package com.mow.controller;

import com.mow.utils.JSONBuilder;
import com.mysql.cj.protocol.Resultset;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureMockMvc
@SpringBootTest
@Disabled
public class MemberControllerTests {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    void setup() {
        this.mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(roles = "MEMBER")
    void getMembers() throws Exception {
        mvc.perform(get("/api/v1/member/members")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "MEMBER")
    void orderMealsBadRequest() throws Exception {
        mvc.perform(post("/api/v1/member/order-meals")).andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "MEMBER")
    void rateServiceBadRequest() throws Exception {
        mvc.perform(put("/api/v1/member/rate-service")).andExpect(status().isBadRequest());
    }

}
