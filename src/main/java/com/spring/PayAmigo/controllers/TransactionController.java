package com.spring.PayAmigo.controllers;

import com.spring.PayAmigo.entities.Transaction;
import com.spring.PayAmigo.entities.TransactionDTO;
import com.spring.PayAmigo.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping("/add_transaction")
    public ResponseEntity<?> addTransaction(@RequestBody TransactionDTO transactionDTO) {
        String response = transactionService.addTransaction(transactionDTO);

        switch (response) {
            case "No destination found", "No source found", "Not enough money in the balance" -> {
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            case "Transaction saved" -> {
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            }
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<Transaction>> getAll () {
        return new ResponseEntity<>(transactionService.getAllTransaction(), HttpStatus.OK);
    }
}
