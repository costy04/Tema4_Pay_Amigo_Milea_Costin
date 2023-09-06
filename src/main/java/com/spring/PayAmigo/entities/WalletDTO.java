package com.spring.PayAmigo.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WalletDTO {
    private String name;
    private Double balance;
    private Long user_id;
}
