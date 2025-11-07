package com.example.sondagecoincafe.bll;

import com.example.sondagecoincafe.bll.impl.QuestionServiceImpl;
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
    private QuestionServiceImpl questionServiceImpl;

    @Test
    void returns_zero_on_null_list() {
        assertThat(questionServiceImpl.calculateAverageRating(null)).isEqualTo(0f);
    }

    @Test
    void returns_zero_on_empty_list() {
        assertThat(questionServiceImpl.calculateAverageRating(List.of())).isEqualTo(0f);
    }

    @Test
    void average_single_question_ok() {
        Question q = q();
        // scores: 4★ (3 votes), 5★ (1 vote) => sum = 4*3 + 5*1 = 17
        q.getScores().add(score(4, 3));
        q.getScores().add(score(5, 1));
        // periods total votes = 4
        q.getPeriods().add(period(4));

        float avg = questionServiceImpl.calculateAverageRating(List.of(q));
        assertThat(avg).isCloseTo(4.25f, offset(1e-6f));
    }

    @Test
    void average_multiple_questions_aggregates_all() {
        Question q1 = q();
        q1.getScores().add(score(3, 2)); // 3*2=6
        q1.getScores().add(score(5, 1)); // 5*1=5  → sum=11
        q1.getPeriods().add(period(3));  // votes=3

        Question q2 = q();
        q2.getScores().add(score(4, 4)); // 4*4=16
        q2.getPeriods().add(period(4));  // votes=4

        // totalSum = 11 + 16 = 27 ; totalVotes = 3 + 4 = 7 ; avg = 27/7 ≈ 3.8571429
        float avg = questionServiceImpl.calculateAverageRating(List.of(q1, q2));
        assertThat(avg).isCloseTo(27f/7f, offset(1e-6f));
    }

    @Test
    void returns_zero_when_totalVotes_is_zero() {
        Question q = q();
        q.getScores().add(score(5, 0)); // 0 vote
        q.getPeriods().add(period(0));  // 0 vote total

        float avg = questionServiceImpl.calculateAverageRating(List.of(q));
        assertThat(avg).isEqualTo(0f);
    }

    // ---------- helpers ----------
    private Question q() {
        Question q = new Question();
        q.setScores(new HashSet<>());
        q.setPeriods(new HashSet<>());
        return q;
    }

    private Score score(int value, int votes) {
        Score s = new Score();
        s.setScore(value);
        s.setScoreVoteCount(votes);
        return s;
    }

    private Period period(int totalVotes) {
        Period p = new Period();
        // Si ton getter/setter est BigDecimal, adapte en conséquence :
        // p.setPeriodTotalVotes(new BigDecimal(totalVotes));
        // et fais évoluer la méthode calcul pour caster en int.
        p.setPeriodTotalVotes(totalVotes);
        return p;
    }
}

@SpringBootTest
class GetListVotesWithScoreTest {

    @Autowired
    private QuestionServiceImpl questionServiceImpl;

    @Test
    void returns_empty_map_when_input_is_null() {
        assertThat(questionServiceImpl.getListVotesWithScore(null)).isEmpty();
    }

    @Test
    void returns_empty_map_when_list_is_empty() {
        assertThat(questionServiceImpl.getListVotesWithScore(List.of())).isEmpty();
    }

    @Test
    void excludes_score_zero_and_includes_positives() {
        Question q = q(
                score(0, 12),  // doit être exclu
                score(1, 3),
                score(5, 7)
        );

        Map<Integer,Integer> out = questionServiceImpl.getListVotesWithScore(List.of(q));
        assertThat(out).doesNotContainKeys(0);
        assertThat(out).containsEntry(1, 3)
                .containsEntry(5, 7);
    }

    @Test
    void keeps_scores_with_zero_votes_if_score_positive() {
        // le besoin ne demande pas d'exclure votes==0, seulement score==0
        Question q = q(score(2, 0), score(1, 5));

        Map<Integer,Integer> out = questionServiceImpl.getListVotesWithScore(List.of(q));

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
