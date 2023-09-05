package com.spring.PayAmigo;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@TestPropertySource(locations = "classpath:application.properties")
@AutoConfigureMockMvc
class PayAmigoApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void getAllUsers() throws Exception {
		MvcResult result = mockMvc.perform(get("/api/users")
						.contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		String content = result.getResponse().getContentAsString();
		System.out.println(content);

	}

	@Test
	void getAllWallets() throws Exception {
		MvcResult result = mockMvc.perform(get("/api/wallets")
						.contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		String content = result.getResponse().getContentAsString();
		System.out.println(content);

	}

	@Test
	//Creating a user
	//It should return CREATED (201)
	void createUser() throws Exception {
		// Exemplu de conținut JSON pentru cererea POST
		String jsonContent = "{"
				+ "\"name\": \"John Doe\","
				+ "\"email\": \"john.doe@example.com\","
				+ "\"password\": \"secretpassword\""
				+ "}";

		MvcResult result = mockMvc.perform(post("/api/add_user")
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonContent))
				.andReturn();

		int httpStatus = result.getResponse().getStatus();
		assert httpStatus == 201;
	}

	//Creating a wallet assigned to a user of which ID doesn't exist
	//It should return BAD REQUEST (400)
	@Test
	void createWalletAssociatedWithNonExistentUser() throws Exception {
		String jsonContent = "{"
				+ "\"name\": \"John\","
				+ "\"balance\": 100.22,"
				+ "\"user_id\": 2"
				+ "}";

		MvcResult result = mockMvc.perform(post("/api/add_wallet")
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonContent))
				.andReturn();

		int httpStatus = result.getResponse().getStatus();
		assertEquals(httpStatus, 400);
	}

	@Test
	void createTransactionWithAmountHigherThanBalance() throws Exception {
		String jsonContent = "{"
				+ "\"source_id\": \"John\","
				+ "\"destination_id\": 11,"
				+ "\"amount\": 1522"
				+ "}";

		MvcResult result = mockMvc.perform(post("/api/add_wallet")
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonContent))
				.andReturn();

		int httpStatus = result.getResponse().getStatus();
		assertEquals(httpStatus, 400);
	}

	@Test
	void createWallets() throws Exception {
		String jsonContent = "{"
				+ "\"name\": \"John\","
				+ "\"balance\": 1000,"
				+ "\"user_id\": 1"
				+ "}";
	}

}
