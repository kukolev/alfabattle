package ru.alfabattle.kukolev.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BranchDto {
    private Long id;
    private String title;
    private BigDecimal lon;
    private BigDecimal lat;
    private String address;
}
