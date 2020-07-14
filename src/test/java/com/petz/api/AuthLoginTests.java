package com.petz.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AuthLoginTests {

	private final String username = "john.admin@ps.org";
	private final String password = "Test";

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void doLoginTest() throws Exception {
		this.mockMvc.perform(post("/auth/login")
				.header("username", username)
				.header("password", password)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}

}
