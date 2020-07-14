package com.petz.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UsersTests {

	private final String username = "john.admin@ps.org";
	private final String password = "Test";

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void doUsersListTest() throws Exception {
		final MvcResult result = this.mockMvc.perform(post("/auth/login")
				.header("username", username)
				.header("password", password)
				.accept(MediaType.APPLICATION_JSON)).andReturn();

		MockHttpServletResponse response = result.getResponse();
		Login loginResource = objectMapper.readValue(response.getContentAsByteArray(), Login.class);

		final String token = loginResource.getAccessToken().getToken();

		this.mockMvc.perform(get("/api/users")
				.header("X-Authorization", "Bearer " + token)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}

}
