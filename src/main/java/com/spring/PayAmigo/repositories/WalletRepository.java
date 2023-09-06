package com.spring.PayAmigo.repositories;

import com.spring.PayAmigo.entities.Wallet;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    @Query("SELECT w FROM Wallet w WHERE w.user.user_id = :userId")
    List<Wallet> findWalletsByUserId(Long userId);

    @Query("SELECT w FROM Wallet w WHERE w.balance = 0")
    List<Wallet> findEmptyWallets();

    @Modifying
    @Transactional
    @Query("UPDATE Wallet w SET w.balance = ?2 WHERE w.id = ?1")
    void updateBalance(Long id, Double value);

    Wallet findByName(String name);


}
