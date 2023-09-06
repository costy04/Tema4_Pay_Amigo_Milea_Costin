package com.spring.PayAmigo.TransactionTesting;

import com.spring.PayAmigo.services.TransactionService;
import com.spring.PayAmigo.services.UserService;
import com.spring.PayAmigo.services.WalletService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@TestPropertySource(locations = "classpath:application.yaml")
@AutoConfigureMockMvc
public class TransactionTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WalletService walletService;
    @Autowired
    private UserService userService;
    @Autowired
    private TransactionService transactionService;

    //Trying to create a transaction with the amount higher than the balance of the source wallet
    //It should return BAD_REQUEST
    @Test
    void createTransactionWithAmountHigherThanBalance() throws Exception {
        String jsonContent = "{"
                + "\"source_id\": 1,"
                + "\"destination_id\": 2,"
                + "\"amount\": 1522.10"
                + "}";

        MvcResult result = mockMvc.perform(post("/api/add_transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andReturn();

        int httpStatus = result.getResponse().getStatus();
        assertEquals(httpStatus, 400);
    }

    @Test
    void createTransactionWithNoDestination() throws Exception {
        String jsonContent = "{"
                + "\"source_id\": 1,"
                + "\"destination_id\": 22,"
                + "\"amount\": 1522.10"
                + "}";

        MvcResult result = mockMvc.perform(post("/api/add_transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andReturn();

        assertEquals("No destination found", result.getResponse().getContentAsString());
    }

    @Test
    void createTransactionWithNoSource() throws Exception {
        String jsonContent = "{"
                + "\"source_id\": 111,"
                + "\"destination_id\": 2,"
                + "\"amount\": 1522.10"
                + "}";

        MvcResult result = mockMvc.perform(post("/api/add_transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andReturn();

        assertEquals("No source found", result.getResponse().getContentAsString());
    }

    @Test
    void createTransactionWithSameSourceAndDestination() throws Exception {
        String jsonContent = "{"
                + "\"source_id\": 1,"
                + "\"destination_id\": 1,"
                + "\"amount\": 1522.10"
                + "}";

        MvcResult result = mockMvc.perform(post("/api/add_transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andReturn();

        assertEquals("A user cannot pay itself", result.getResponse().getContentAsString());
    }

    @Test
    void createTransactionWithNegativeAmount() throws Exception {
        String jsonContent = "{"
                + "\"source_id\": 1,"
                + "\"destination_id\": 2,"
                + "\"amount\": -1522.10"
                + "}";

        MvcResult result = mockMvc.perform(post("/api/add_transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andReturn();

        assertEquals("Cannot pay a negative amount", result.getResponse().getContentAsString());
    }
}
