package com.spring.PayAmigo.controllers;

import com.spring.PayAmigo.entities.Wallet;
import com.spring.PayAmigo.entities.WalletDTO;
import com.spring.PayAmigo.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class WalletController {

    @Autowired
    WalletService walletService;

    @PostMapping("/add_wallet")
    public ResponseEntity<?> addWallet (@RequestBody WalletDTO walletDTO){
        Wallet response = walletService.addWallet(walletDTO);
        if (response == null) {
            return new ResponseEntity<>("The userID that is assign to this wallet doesn't exist", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping ("/wallets")
    public ResponseEntity<List<Wallet>> getAllWallets () {
        return new ResponseEntity<>(walletService.getAllWallets(), HttpStatus.OK);
    }

    @GetMapping ("/wallets/find")
    public ResponseEntity<List<Wallet>> getWalletByUserId (@RequestParam (name = "id") Long id){
        return new ResponseEntity<>(walletService.findByUserId(id), HttpStatus.OK);
    }
}
