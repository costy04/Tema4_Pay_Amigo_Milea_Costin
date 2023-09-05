package com.spring.PayAmigo.entities;

import lombok.Data;

@Data
public class TransactionDTO {
    private Long source_id;
    private Long destination_id;
    private Double amount;
}
