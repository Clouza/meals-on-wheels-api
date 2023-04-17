package com.mow.controller;

import com.mow.service.UsersService;
import com.mow.utils.JSONBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class BaseControllerTests {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mvc;

    @Autowired
    UsersService usersService;

    @Autowired
    private JSONBuilder jsonBuilder;

    @BeforeEach
    void setup() throws Exception {
        this.mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        this.jsonBuilder
                .put("username", "dummy")
                .put("password", "dummy")
                .put("email", "dummy@mail.io");
    }

    @Test
    void registrationUsernameTaken() throws Exception {
        mvc.perform(post("/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.jsonBuilder.stringify())
        ).andExpect(status().isNotFound());
    }

    @Test
    void registrationEmailTaken() throws Exception {
        mvc.perform(post("/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.jsonBuilder.stringify())
        ).andExpect(status().isNotFound());
    }

    @Test
    void addUserToPartnerByValidUsername() throws Exception { // will get result as expected if record on table partners not exists
        mvc.perform(post("/register-partner")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBuilder.put("username", "dummy").stringify())
        ).andExpect(status().isOk());
    }

    @Test
    void addUserToPartnerByInvalidUsername() throws Exception { // will get result as expected if record on table partners exists
        mvc.perform(post("/register-partner")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBuilder.put("username", "dummy").stringify())
        ).andExpect(status().isNotAcceptable());
    }

}
