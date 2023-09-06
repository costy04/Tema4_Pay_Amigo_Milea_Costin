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
import java.util.Objects;

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

        if (Objects.equals(destinationWallet.getId(), sourceWallet.getId())) {
            return "A user cannot pay itself";
        }

        if (transactionDTO.getAmount() < 0) {
            return "Cannot pay a negative amount";
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
