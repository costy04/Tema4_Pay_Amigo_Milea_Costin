package com.spring.PayAmigo.entities;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransactionDTO {
    private Long source_id;
    private Long destination_id;
    private Double amount;
    private LocalDateTime created_at;
}
