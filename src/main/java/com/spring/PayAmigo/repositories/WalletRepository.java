package com.spring.PayAmigo.repositories;

import com.spring.PayAmigo.entities.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    @Query("SELECT w FROM Wallet w WHERE w.user.user_id = :userId")
    List<Wallet> findWalletsByUserId(Long userId);
}
