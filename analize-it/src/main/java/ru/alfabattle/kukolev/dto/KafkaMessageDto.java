package ru.alfabattle.kukolev.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class KafkaMessageDto {
    private String ref;
    private Long categoryId;
    private String userId;
    private String recipientId;
    private String desc;
    private BigDecimal amount;
}
