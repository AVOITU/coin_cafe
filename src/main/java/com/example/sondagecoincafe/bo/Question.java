package com.example.sondagecoincafe.bo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "result_id", columnDefinition = "int UNSIGNED not null")
    private Long id;

    @Size(max = 200)
    @NotNull
    @Column(name = "question_text", nullable = false, length = 200)
    private String questionText;

    @NotNull
    @Column(name = "question_total_votes", nullable = false)
    private Integer questionTotalVotes;

    @Size(max = 2000)
    @Column(name = "chatgpt_comments", length = 2000)
    private String chatgptComments;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "all_votes_count", nullable = false)
    private Integer allVotesCount;

}