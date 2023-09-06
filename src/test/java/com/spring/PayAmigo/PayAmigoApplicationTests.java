package com.spring.PayAmigo;

import com.spring.PayAmigo.controllers.WalletController;
import com.spring.PayAmigo.entities.User;
import com.spring.PayAmigo.entities.Wallet;
import com.spring.PayAmigo.entities.WalletDTO;
import com.spring.PayAmigo.repositories.UserRepository;
import com.spring.PayAmigo.services.TransactionService;
import com.spring.PayAmigo.services.UserService;
import com.spring.PayAmigo.services.WalletService;
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

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@TestPropertySource(locations = "classpath:application.yaml")
@AutoConfigureMockMvc
class PayAmigoApplicationTests {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private WalletService walletService;
	@Autowired
	private UserService userService;
	@Autowired
	private TransactionService transactionService;

	@Test
	void createWallets() throws Exception {
		String jsonContent = "{"
				+ "\"name\": \"John\","
				+ "\"balance\": 1000,"
				+ "\"user_id\": 1"
				+ "}";
	}

}
