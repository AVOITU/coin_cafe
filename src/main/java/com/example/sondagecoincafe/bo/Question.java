package com.example.sondagecoincafe.bo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "questions")
@Getter @Setter @EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Question {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "question_id")
    private Long id;

    @NotNull @Size(max = 200)
    @Column(name = "question_text", nullable = false, length = 200)
    private String questionText;

    @NotNull @Column(name = "question_total_votes", nullable = false)
    @PositiveOrZero
    private Integer questionTotalVotes;

    @Size(max = 2000)
    @Column(name = "chatgpt_comments", length = 2000)
    private String chatgptComments;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "all_votes_count", nullable = false)
    @PositiveOrZero
    private Integer allVotesCount;

    @NotEmpty
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "questions_periods",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "period_id"))
    @ToString.Exclude
    private Set<Period> periods = new HashSet<>();

    @NotEmpty
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "questions_scores",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "score_id"))
    private Set<Score> scores = new HashSet<>();
}