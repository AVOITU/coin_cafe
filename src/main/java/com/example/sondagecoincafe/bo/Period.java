package com.example.sondagecoincafe.bo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
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

@Entity @Table(name="periods", uniqueConstraints = {
        @UniqueConstraint(name = "uk_timestamp_period_periods", columnNames = {"timestamp_period"})
})
@Getter @Setter
public class Period {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="period_id")                // pas UNSIGNED
    private Long id;

    @CreationTimestamp
    @Column(name="timestamp_period", updatable=false,
            columnDefinition="datetime(6) default current_timestamp(6)")
    private LocalDateTime timestampPeriod;

    @Column(name="period_total_votes", precision=4, scale=2)
    @PositiveOrZero
    private int periodTotalVotes;

    @ManyToMany(mappedBy="periods", fetch=FetchType.LAZY)
    @ToString.Exclude
    private Set<Question> questions = new HashSet<>();

    @PrePersist
    void prePersist() {
        if (timestampPeriod == null) {
            timestampPeriod = LocalDateTime.now();
        }
    }

    @Override public boolean equals(Object o){
        if (this == o) return true;
        if (!(o instanceof Period other)) return false;
        if (this.id == null || other.id == null) return false;
        return this.id.equals(other.id);
    }
    @Override public int hashCode(){ return id != null ? id.hashCode() : System.identityHashCode(this); }
}