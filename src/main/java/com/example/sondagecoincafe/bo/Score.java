package com.example.sondagecoincafe.bo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "scores")
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "score_id", columnDefinition = "int UNSIGNED not null")
    private Long id;

    @NotNull
    @Column(name = "score", nullable = false)
    private Integer score;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "score_vote_count", nullable = false)
    private Integer scoreVoteCount;

}