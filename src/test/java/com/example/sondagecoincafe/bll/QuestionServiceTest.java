package com.example.sondagecoincafe.bll;

import com.example.sondagecoincafe.bo.Period;
import com.example.sondagecoincafe.bo.Question;
import com.example.sondagecoincafe.bo.Score;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.offset;

@SpringBootTest
class RatingCalculatorTest {

    @Autowired
    private QuestionService questionService;

    @Test
    void returns_zero_on_null_list() {
        assertThat(questionService.calculateAverageRating(null, 10)).isEqualTo(0f);
    }

    @Test
    void returns_zero_when_totalVotes_is_zero_even_with_scores() {
        Question q = q();
        q.getScores().add(score(5, 3)); // pondéré = 15
        assertThat(questionService.calculateAverageRating(List.of(q), 0)).isEqualTo(0f);
    }

    @Test
    void returns_zero_on_empty_list() {
        assertThat(questionService.calculateAverageRating(List.of(), 0)).isEqualTo(0f);
    }

    @Test
    void average_single_question_ok() {
        Question q = q();
        q.getScores().add(score(4, 3)); // 12
        q.getScores().add(score(5, 1)); // +5 = 17
        float avg = questionService.calculateAverageRating(List.of(q), 4);
        assertThat(avg).isCloseTo(4.25f, offset(1e-6f));
    }

    @Test
    void average_multiple_questions_aggregates_all() {
        Question q1 = q();
        q1.getScores().add(score(3, 2)); // 6
        q1.getScores().add(score(5, 1)); // +5 = 11

        Question q2 = q();
        q2.getScores().add(score(4, 4)); // 16

        float avg = questionService.calculateAverageRating(List.of(q1, q2), 7); // 27/7
        assertThat(avg).isCloseTo(27f/7f, offset(1e-6f));
    }

    // helpers
    private Question q() {
        Question q = new Question();
        q.setScores(new java.util.HashSet<>());
        q.setPeriods(new java.util.HashSet<>());
        q.setQuestionText("q");
        q.setQuestionTotalVotes(0);
        q.setAllVotesCount(0);
        return q;
    }
    private Score score(int value, int votes) {
        Score s = new Score();
        s.setScore(value);
        s.setScoreVoteCount(votes);
        return s;
    }
}

@SpringBootTest
class GetListVotesWithScoreTest {

    @Autowired
    private QuestionService questionService;

    @Test
    void returns_empty_map_when_input_is_null() {
        assertThat(questionService.getListVotesWithScore(null)).isEmpty();
    }

    @Test
    void returns_empty_map_when_list_is_empty() {
        assertThat(questionService.getListVotesWithScore(List.of())).isEmpty();
    }

    @Test
    void excludes_score_zero_and_includes_positives() {
        Question q = q(
                score(0, 12),  // doit être exclu
                score(1, 3),
                score(5, 7)
        );

        Map<Integer,Integer> out = questionService.getListVotesWithScore(List.of(q));
        assertThat(out).doesNotContainKeys(0);
        assertThat(out).containsEntry(1, 3)
                .containsEntry(5, 7);
    }

    @Test
    void keeps_scores_with_zero_votes_if_score_positive() {
        // le besoin ne demande pas d'exclure votes==0, seulement score==0
        Question q = q(score(2, 0), score(1, 5));

        Map<Integer,Integer> out = questionService.getListVotesWithScore(List.of(q));

        assertThat(out).containsEntry(1, 5)
                .containsEntry(2, 0);   // accepté car score>0
        assertThat(out).doesNotContainKey(0);
    }

    // ---------- helpers ----------
    private Question q(Score... scores) {
        Question q = new Question();
        q.setScores(new HashSet<>(Arrays.asList(scores)));
        q.setPeriods(new HashSet<>());
        q.setQuestionText("q");
        q.setQuestionTotalVotes(0);
        q.setAllVotesCount(0);
        return q;
    }

    private Score score(int value, int votes) {
        Score s = new Score();
        s.setScore(value);
        s.setScoreVoteCount(votes);
        return s;
    }
}
