package com.example.sondagecoincafe.bo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "periods")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Period {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "period_id", columnDefinition = "int UNSIGNED not null")
    private Long id;

    @CreationTimestamp
    @NotNull
    @Column(name = "timestamp_period", nullable = true, updatable = true,
            columnDefinition = "datetime(6)")
    private LocalDateTime timestampPeriod;

    @Column(name = "period_total_votes", precision = 4, scale = 2)
    private BigDecimal periodTotalVotes;

    @ManyToMany(mappedBy = "periods", fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Question> questions = new HashSet<>();
}