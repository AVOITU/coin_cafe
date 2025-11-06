package com.example.sondagecoincafe.dal;

import com.example.sondagecoincafe.bo.Question;
import com.example.sondagecoincafe.bo.Score;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Set;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class ScoreDaoTest {

    @Autowired private ScoreDao scoreDao;
    @Autowired private QuestionDao questionDao;
    @Autowired private EntityManager em;

    @BeforeEach
    void setup() {
        scoreDao.deleteAll();
        questionDao.deleteAll();
    }

    @Test
    void save_and_find_all_scores_ok() {
        Score s1 = newScore(3);
        Score s2 = newScore(5);
        scoreDao.saveAll(List.of(s1, s2));

        List<Score> all = scoreDao.findAll();
        assertThat(all).hasSize(2);
        assertThat(all).extracting("score").containsExactlyInAnyOrder(3, 5);
    }

    @Test
    void link_scores_to_question_ok() {
        // given
        Score s4 = scoreDao.save(newScore(4));
        Score s5 = scoreDao.save(newScore(5));

        Question q = new Question();
        q.setQuestionText("Ambiance");
        q.setQuestionTotalVotes(0);
        q.setAllVotesCount(0);
        q.getScores().addAll(Set.of(s4, s5));
        questionDao.saveAndFlush(q);

        // when
        Question reloaded = questionDao.findById(q.getId()).orElseThrow();

        // then
        assertThat(reloaded.getScores()).hasSize(2);
        assertThat(reloaded.getScores())
                .extracting("score")
                .contains(4, 5);
    }

//    @Test
//    void delete_score_does_not_delete_question() {
//        Score s = scoreDao.save(newScore(2));
//        Question q = new Question();
//        q.setQuestionText("Accessibilité");
//        q.setQuestionTotalVotes(0);
//        q.setAllVotesCount(0);
//        q.getScores().add(s);
//        questionDao.saveAndFlush(q);
//
//        long countBefore = questionDao.count();
//
//        scoreDao.delete(s);
//        em.clear();
//        scoreDao.flush();
//
//        assertThat(questionDao.count()).isEqualTo(countBefore);
//        assertThat(scoreDao.findById(s.getId())).isNotPresent();
//    }

    private Score newScore(int value) {
        Score s = new Score();
        s.setScore(value);
        s.setScoreVoteCount(0);
        return s;
    }
}