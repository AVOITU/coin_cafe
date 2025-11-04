package com.example.sondagecoincafe.bo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "periods")
public class Period {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "period_id", columnDefinition = "int UNSIGNED not null")
    private Long id;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @CreationTimestamp
    private LocalDateTime timestampPeriod;

    @Column(name = "period_total_votes", precision = 4, scale = 2)
    private BigDecimal periodTotalVotes;

}