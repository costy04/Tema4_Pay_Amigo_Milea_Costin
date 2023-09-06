package com.spring.PayAmigo.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn (name = "source_wallet_id")
    private Wallet sourceWallet;

    @ManyToOne
    @JoinColumn (name = "destination_wallet_id")
    private Wallet destinationWallet;

    private Double amount;

    private LocalDateTime created_at;



}
