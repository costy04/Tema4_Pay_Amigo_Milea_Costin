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
        return walletRepository.save(wallet);
    }

    public List<Wallet> findByUserId (Long id) {
        return walletRepository.findWalletsByUserId(id);
    }
}
