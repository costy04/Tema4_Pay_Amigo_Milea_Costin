package com.spring.PayAmigo.UserTesting;

import com.spring.PayAmigo.entities.User;
import com.spring.PayAmigo.services.TransactionService;
import com.spring.PayAmigo.services.UserService;
import com.spring.PayAmigo.services.WalletService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.Duration;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@TestPropertySource(locations = "classpath:application.yaml")
@AutoConfigureMockMvc
public class UserTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WalletService walletService;
    @Autowired
    private UserService userService;
    @Autowired
    private TransactionService transactionService;

    @Test
    void getAllUsers() throws Exception {

        assertTimeout(Duration.ofMillis(300), () -> {
            MvcResult result = mockMvc.perform(get("/api/users")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andReturn();
            String content = result.getResponse().getContentAsString();
            System.out.println(content);
        }, "Performance issues with getAllUsers() method !");

    }

    @Test

//  Creating a user
//  It should return CREATED (201)
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

    @Test
    void itShouldThrowNullPointerException () {
        User user = userService.getByName("Gigel");

//      User "Gigel" doesn't exist in the database
        Throwable thrown = assertThrows(NullPointerException.class,
                ()->{
                    String name = user.getName();
                });

    }


}
