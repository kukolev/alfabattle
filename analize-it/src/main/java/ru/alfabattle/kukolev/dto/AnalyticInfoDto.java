package ru.alfabattle.kukolev.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnalyticInfoDto {
    private BigDecimal min = new BigDecimal(Long.MAX_VALUE);
    private BigDecimal max = new BigDecimal(0);
    private BigDecimal sum = new BigDecimal(0);
}
