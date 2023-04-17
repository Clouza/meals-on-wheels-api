package com.mow.controller;

import com.mow.utils.JSONBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureMockMvc
@SpringBootTest
@ExtendWith({ RestDocumentationExtension.class, SpringExtension.class})
public class AdminControllerTests {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    void setup(RestDocumentationContextProvider restDocumentation) {
        this.mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getPartners() throws Exception {
        mvc.perform(get("/api/v1/admin/partners/false")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getMembers() throws Exception {
        mvc.perform(get("/api/v1/admin/members/false")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getMeals() throws Exception {
        mvc.perform(get("/api/v1/admin/meals/false")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getDonators() throws Exception {
        mvc.perform(get("/api/v1/admin/donators")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getRiders() throws Exception {
        mvc.perform(get("/api/v1/admin/riders/false")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void approve() throws Exception {
        JSONBuilder builder = new JSONBuilder();
        String json = builder.put("id", 1L).put("type", "member").stringify();

        mvc.perform(
                put("/api/v1/admin/approve")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(status().isAccepted()).andDo(document("{methodName}",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()))
        );
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteUserById() throws Exception { // record must exist first
        mvc.perform(delete("/api/v1/admin/delete/" + 2L)).andExpect(status().isOk());
    }

}
