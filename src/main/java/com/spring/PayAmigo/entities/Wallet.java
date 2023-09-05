package com.spring.PayAmigo.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "wallet")
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn (name = "user_id")
    private User user;

    private Double balance;

}
