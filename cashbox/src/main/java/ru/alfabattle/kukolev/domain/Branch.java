package ru.alfabattle.kukolev.domain;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "branches")
public class Branch {
    @Id
    private Long id;
    @Column
    private String title;
    @Column
    private BigDecimal lon;
    @Column
    private BigDecimal lat;
    @Column
    private String address;
}
