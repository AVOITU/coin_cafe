package com.example.sondagecoincafe.bll;

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
