package com.spring.PayAmigo.WalletTesting;

import com.spring.PayAmigo.entities.Wallet;
import com.spring.PayAmigo.entities.WalletDTO;
import com.spring.PayAmigo.services.TransactionService;
import com.spring.PayAmigo.services.UserService;
import com.spring.PayAmigo.services.WalletService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@TestPropertySource(locations = "classpath:application.yaml")
@AutoConfigureMockMvc
public class WalletTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WalletService walletService;
    @Autowired
    private UserService userService;
    @Autowired
    private TransactionService transactionService;

    //Creating a wallet assigned to a user of which ID doesn't exist
    //It should return BAD REQUEST (400)
    @Test
    void createWalletAssociatedWithNonExistentUser() throws Exception {
        String jsonContent = "{"
                + "\"name\": \"John\","
                + "\"balance\": 100.22,"
                + "\"user_id\": 27"
                + "}";

        MvcResult result = mockMvc.perform(post("/api/add_wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andReturn();

        int httpStatus = result.getResponse().getStatus();
        assertEquals(httpStatus, 400);
    }

    @Test
    void getAllWalletsByUserId () {

//		Get the ID for the user "Vasile" that we already created
        Long user_id = userService.getIdByName("Vasile");

//		Create a fist wallet for "Vasile"
        WalletDTO walletDTO1 = new WalletDTO();
        walletDTO1.setUser_id(user_id);
        walletDTO1.setName("vasile_wallet");
        walletDTO1.setBalance(230.0);
        Wallet wallet1 = walletService.addWallet(walletDTO1);

//		Create the second wallet for "Vasile"
        WalletDTO walletDTO2 = new WalletDTO();
        walletDTO2.setUser_id(user_id);
        walletDTO2.setName("vasile_wallet2");
        walletDTO2.setBalance(230.0);
        Wallet wallet2 = walletService.addWallet(walletDTO2);

//		Concatenate these 2 wallets into a list
        List<Wallet> wallets = Arrays.asList(wallet1, wallet2);
//		System.out.println(wallets);

//		Create a list with the wallets Ids
        List<Long> walletsIds = new ArrayList<>();
        for (Wallet wallet : wallets) {
            walletsIds.add(wallet.getId());
        }

//		Get from the database all the wallets assigned to Vasile
        List<Wallet> wallets_from_db = walletService.findByUserId(user_id);
//		System.out.println(wallets_from_db);
//		Create a list with the wallets Ids from db
        List<Long> wallets_from_dbIds = new ArrayList<>();
        for (Wallet wallet : wallets_from_db) {
            wallets_from_dbIds.add(wallet.getId());
        }

        assertArrayEquals(walletsIds.stream().mapToLong(l -> l).toArray(), wallets_from_dbIds.stream().mapToLong(l -> l).toArray());

    }

}
