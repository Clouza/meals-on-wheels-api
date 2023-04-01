package com.mow;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@AutoConfigureMockMvc
@SpringBootTest
class MealsOnWheelsApiApplicationTests {

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
	void contextLoads() {
		assertNotNull(context);
	}
	
	@Test
	@WithMockUser(username = "user", password = "user")
	void rootEndpoint() throws Exception {
		mvc.perform(get("/")).andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser(username = "user", password = "user", roles = {"ADMIN"})
	void adminEnpoint() throws Exception {
		mvc.perform(get("/api/v1/admin/")).andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser(username = "user", password = "user", roles = {"MEMBER"})
	void memberEnpoint() throws Exception {
		mvc.perform(get("/api/v1/member/")).andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser(username = "user", password = "user", roles = {"PARTNER"})
	void partnerEnpoint() throws Exception {
		mvc.perform(get("/api/v1/partner/")).andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser(username = "user", password = "user", roles = {"RIDER"})
	void riderEnpoint() throws Exception {
		mvc.perform(get("/api/v1/rider/")).andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser(username = "user", password = "user", roles = {"DONATOR"})
	void donatorEnpoint() throws Exception {
		mvc.perform(get("/api/v1/c/")).andExpect(status().isOk());
	}
	

}
