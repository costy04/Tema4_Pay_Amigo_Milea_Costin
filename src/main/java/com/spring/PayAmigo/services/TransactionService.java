package com.spring.PayAmigo.services;

import com.spring.PayAmigo.entities.Transaction;
import com.spring.PayAmigo.entities.TransactionDTO;
import com.spring.PayAmigo.entities.Wallet;
import com.spring.PayAmigo.repositories.TransactionRepository;
import com.spring.PayAmigo.repositories.WalletRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private WalletRepository walletRepository;

    public List<Transaction> getAllTransaction () {
        return transactionRepository.findAll();
    }

    public String addTransaction (TransactionDTO transactionDTO) {
        Transaction transaction = new Transaction();
        transaction.setAmount(transactionDTO.getAmount());

        Wallet sourceWallet = walletRepository.findById(transactionDTO.getSource_id()).orElse(null);
        Wallet destinationWallet = walletRepository.findById(transactionDTO.getDestination_id()).orElse(null);

        if (destinationWallet == null) {
            return "No destination found";
        }
        else if (sourceWallet == null) {
            return "No source found";
        }

        if (sourceWallet.getBalance() < transactionDTO.getAmount()) {
            return "Not enough money in the balance";
        }

        transaction.setSourceWallet(sourceWallet);
        transaction.setDestinationWallet(destinationWallet);
        transactionRepository.save(transaction);

        return "Transaction saved";
    }

}
