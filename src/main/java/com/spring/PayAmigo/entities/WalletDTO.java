package com.spring.PayAmigo.entities;

import lombok.Data;

@Data
public class WalletDTO {
    private String name;
    private Double balance;
    private Long user_id;
}
