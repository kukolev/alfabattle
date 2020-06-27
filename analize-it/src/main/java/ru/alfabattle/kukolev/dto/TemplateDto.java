package ru.alfabattle.kukolev.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class TemplateDto {
   private String recipientId;
   private Long categoryId;
   private BigDecimal amount;
}
