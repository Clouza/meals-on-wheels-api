package com.mow.controller;

import com.mysql.cj.protocol.Resultset;
import org.junit.jupiter.api.BeforeEach;
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
    @WithMockUser(username = "user", password = "user", roles = {"MEMBER"})
    void getMembers() throws Exception {
        mvc.perform(get("/api/v1/member/members")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", password = "user", roles = {"MEMBER"})
    void getImageWithoutParam() throws Exception {
        mvc.perform(get("/api/v1/member/get-image")).andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "user", password = "user", roles = {"MEMBER"})
    void getImageWithParam() throws Exception {
        mvc.perform(get("/api/v1/member/get-image")
                .content("test.img")
        ).andExpect(status().isNotFound());
    }

}
