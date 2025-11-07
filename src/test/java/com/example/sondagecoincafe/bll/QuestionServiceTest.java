package com.example.sondagecoincafe.bll;

import com.example.sondagecoincafe.bo.Period;
import com.example.sondagecoincafe.bo.Question;
import com.example.sondagecoincafe.bo.Score;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.offset;

class RatingCalculatorTest {

    // Adapte le nom/classe réelle si besoin
    static class SUT {
        public float calculateAverageRating(List<Question> results) {
            float averageRating = 0;
            int totalSum = 0;
            int totalVotes = 0;

            if (results == null) return 0f;
            for (Question question : results) {
                for (Score score : question.getScores()) {
                    int votes = score.getScoreVoteCount();
                    int value = score.getScore();
                    totalSum += value * votes;
                }
                for (Period period : question.getPeriods()){
                    int numberOfVotes = period.getPeriodTotalVotes(); // suppose int
                    totalVotes += numberOfVotes;
                }
            }
            averageRating = (float) totalSum / totalVotes;
            return totalVotes == 0 ? 0f : averageRating;
        }
    }

    private final SUT service = new SUT();

    @Test
    void returns_zero_on_null_list() {
        assertThat(service.calculateAverageRating(null)).isEqualTo(0f);
    }

    @Test
    void returns_zero_on_empty_list() {
        assertThat(service.calculateAverageRating(List.of())).isEqualTo(0f);
    }

    @Test
    void average_single_question_ok() {
        Question q = q();
        // scores: 4★ (3 votes), 5★ (1 vote) => sum = 4*3 + 5*1 = 17
        q.getScores().add(score(4, 3));
        q.getScores().add(score(5, 1));
        // periods total votes = 4
        q.getPeriods().add(period(4));

        float avg = service.calculateAverageRating(List.of(q));
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
        float avg = service.calculateAverageRating(List.of(q1, q2));
        assertThat(avg).isCloseTo(27f/7f, offset(1e-6f));
    }

    @Test
    void returns_zero_when_totalVotes_is_zero() {
        Question q = q();
        q.getScores().add(score(5, 0)); // 0 vote
        q.getPeriods().add(period(0));  // 0 vote total

        float avg = service.calculateAverageRating(List.of(q));
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
