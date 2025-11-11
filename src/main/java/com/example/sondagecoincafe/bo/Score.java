package com.example.sondagecoincafe.bo;

import com.example.sondagecoincafe.configuration.AppConstants;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "scores", uniqueConstraints = {
        @UniqueConstraint(name = "uk_scores_score", columnNames = {"score"})
})
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "score_id", columnDefinition = "int UNSIGNED not null")
    private Long id;

    @NotNull
    @Min(0) @Max(AppConstants.MAX_SCORE)
    @PositiveOrZero
    @Column(name = "score", nullable = false)
    private Integer score;

    @NotNull
    @ColumnDefault("0")
    @PositiveOrZero
    @Column(name = "score_vote_count", nullable = false)
    private Integer scoreVoteCount;

    @ManyToMany(mappedBy = "scores")
    private Set<Question> questions;
}