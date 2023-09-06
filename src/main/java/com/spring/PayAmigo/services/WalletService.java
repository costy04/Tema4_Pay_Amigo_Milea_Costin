package com.spring.PayAmigo.services;

import com.spring.PayAmigo.entities.User;
import com.spring.PayAmigo.entities.Wallet;
import com.spring.PayAmigo.entities.WalletDTO;
import com.spring.PayAmigo.repositories.UserRepository;
import com.spring.PayAmigo.repositories.WalletRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class WalletService {

    @Autowired
    private final WalletRepository walletRepository;
    @Autowired
    private final UserRepository userRepository;

    public List<Wallet> getAllWallets () {
        return walletRepository.findAll();
    }

    public Wallet addWallet(WalletDTO walletDTO) {
        Wallet wallet = new Wallet();
        wallet.setName(walletDTO.getName());
        wallet.setBalance(walletDTO.getBalance());

        User user = userRepository.findById(walletDTO.getUser_id()).orElse(null);
        wallet.setUser(user);

        if (user == null){
            return null;
        }

        if (walletDTO.getBalance() < 0) {
            throw new RuntimeException("No negative amount allowed");
        }

        return walletRepository.save(wallet);
    }

    public List<Wallet> findByUserId (Long id) {
        return walletRepository.findWalletsByUserId(id);
    }

    public Wallet getWalletByName (String name) {
        return walletRepository.findByName(name);
    }

    public List<Wallet> getEmptyWallets () {
        return walletRepository.findEmptyWallets();
    }

    public Wallet getById (Long id) {
        Optional<Wallet> walletOptional = walletRepository.findById(id);
        if (walletOptional.isPresent()) {
            return walletOptional.get();
        }
        return null;
    }

    public String addMoney (Long id, Double value) {
        Optional<Wallet> walletResponse = walletRepository.findById(id);
        if (walletResponse.isPresent() && value > 0){
            Wallet wallet = walletResponse.get();
            walletRepository.updateBalance(id, wallet.getBalance() + value);
            return "Add succeeded";
        }
        else if (value < 0) {
            return "No negative amounts";
        }
        else {
            return "No user found with ID";
        }
    }

    public String withdrawMoney (Long id, Double value) {
        Optional<Wallet> walletResponse = walletRepository.findById(id);
        if (walletResponse.isPresent() & value > 0){
            Wallet wallet = walletResponse.get();
            if (wallet.getBalance() - value < 0) {
                return "Insufficient funds";
            }
            walletRepository.updateBalance(id, wallet.getBalance() - value);
            return "Withdraw succeeded";
        }
        else if (value < 0) {
            return "No negative amounts";
        }
        else {
            return "No user found with ID";
        }

    }
}
