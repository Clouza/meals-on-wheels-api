package com.mow.controller;

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

@AutoConfigureMockMvc
@SpringBootTest
public class PartnerControllerTests {

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
    @WithMockUser(roles = "PARTNER")
    void getMeals() throws Exception {
        mvc.perform(get("/api/v1/partner/meals")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "PARTNER")
    void getPartners() throws Exception {
        mvc.perform(get("/api/v1/partner/partners")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "PARTNER")
    void getPartner() throws Exception {
        mvc.perform(get("/api/v1/partner/" + 1L)).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "PARTNER")
    void postMealsWithoutParams() throws Exception {
        mvc.perform(post("/api/v1/partner/meals")).andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "PARTNER")
    void deleteMeal() throws Exception {
        mvc.perform(delete("/api/v1/partner/meals/" + 1L))
                .andExpect(status().isOk());
    }

}
