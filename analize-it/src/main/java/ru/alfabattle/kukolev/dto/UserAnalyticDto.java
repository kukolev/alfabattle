package ru.alfabattle.kukolev.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAnalyticDto {
    private String userId;
    private BigDecimal totalSum = new BigDecimal(0);
    private Map<String, AnalyticInfoDto> analyticInfo = new HashMap<>();
}
