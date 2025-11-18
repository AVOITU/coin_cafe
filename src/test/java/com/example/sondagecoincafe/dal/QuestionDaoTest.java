package com.example.sondagecoincafe.dal;

import com.example.sondagecoincafe.bo.Period;
import com.example.sondagecoincafe.bo.Question;
import com.example.sondagecoincafe.bo.Score;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

//Tester avec application.properties en fixant spring.jpa.hibernate.ddl-auto=none
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class QuestionDaoTest {


    private Score s3, s4, s5;
    private Period p1, p2;

    @Autowired
    QuestionDao questionDao;
    @Autowired
    ScoreDao scoreDao;
    @Autowired
    PeriodDao periodDao;

    @BeforeEach
    void setUp() {
        questionDao.deleteAll();
        scoreDao.deleteAll();
        periodDao.deleteAll();

        s3 = scoreDao.save(newScore(3));
        s4 = scoreDao.save(newScore(4));
        s5 = scoreDao.save(newScore(5));

        // Periods
        p1 = periodDao.save(newPeriod(340));
        p2 = periodDao.save(newPeriod(410));
    }

    // ---------- helpers ----------
    private Score newScore(int value) {
        Score s = new Score();
        s.setScore(value);
        s.setScoreVoteCount(0);
        return s;
    }

    private Period newPeriod(int avg) {
        Period p = new Period();
        p.setPeriodTotalVotes(avg);
        return p;
    }

    private Question newQuestion(String txt, int totalVotes, int allVotes) {
        Question q = new Question();
        q.setQuestionText(txt);
        q.setQuestionTotalVotes(totalVotes);
        q.setQuestionTotalScore(allVotes);
        return q;
    }

    @Test
    void save_and_findAll_with_relations_ok() {
        Question q = newQuestion("Hygiène", 0, 0);
        q.getScores().addAll(List.of(s3, s4, s5));
        q.getPeriods().addAll(List.of(p1, p2));

        Question saved = questionDao.saveAndFlush(q);
        assertThat(saved.getId()).isNotNull();

        List<Question> all = questionDao.findAll();
        assertThat(all).hasSize(1);
        Question reloaded = all.get(0);

        assertThat(reloaded.getQuestionText()).isEqualTo("Hygiène");
        assertThat(reloaded.getScores()).extracting("score").containsExactlyInAnyOrder(3, 4, 5);
        assertThat(reloaded.getPeriods()).hasSize(2);
        assertThat(reloaded.getQuestionTotalVotes()).isZero();
        assertThat(reloaded.getQuestionTotalScore()).isZero();
    }

    @Test
    void findById_returns_present_and_absent() {
        Question q = questionDao.saveAndFlush(newQuestion("Accueil", 2, 7));
        Long id = q.getId();
        Optional<Question> found = questionDao.findById(id);
        Optional<Question> absent = questionDao.findById(-999L);

        assertThat(found).isPresent();
        assertThat(found.get().getQuestionText()).isEqualTo("Accueil");
        assertThat(absent).isNotPresent();
    }

    @Test
    void update_question_fields_persists_changes() {
        Question q = questionDao.saveAndFlush(newQuestion("Accessibilité", 1, 3));
        q.setQuestionText("Accessibilité PMR");
        q.setQuestionTotalVotes(5);
        q.setQuestionTotalScore(11);

        Question updated = questionDao.saveAndFlush(q);
        assertThat(updated.getQuestionText()).isEqualTo("Accessibilité PMR");
        assertThat(updated.getQuestionTotalVotes()).isEqualTo(5);
        assertThat(updated.getQuestionTotalScore()).isEqualTo(11);
    }

    @Test
    void add_and_remove_scores_in_many_to_many() {
        Question q = questionDao.saveAndFlush(newQuestion("Ambiance", 0, 0));

        // ajout
        q.getScores().addAll(List.of(s3, s4));
        questionDao.saveAndFlush(q);
        Question afterAdd = questionDao.findById(q.getId()).orElseThrow();
        assertThat(afterAdd.getScores()).extracting("score").containsExactlyInAnyOrder(3, 4);

        // retrait
        afterAdd.getScores().remove(s3);
        questionDao.saveAndFlush(afterAdd);
        Question afterRemove = questionDao.findById(q.getId()).orElseThrow();
        assertThat(afterRemove.getScores()).extracting("score").containsExactly(4);
    }

    @Test
    void link_periods_to_question_and_verify_timestamp() {
        Question q = newQuestion("Propreté", 0, 0);
        q.getPeriods().addAll(List.of(p1, p2));
        q = questionDao.saveAndFlush(q);

        Question reloaded = questionDao.findById(q.getId()).orElseThrow();
        assertThat(reloaded.getPeriods()).hasSize(2);
        // vérifie que les timestamps des periods ne sont pas nuls
        assertThat(reloaded.getPeriods())
                .allSatisfy(period -> assertThat(period.getTimestampPeriod()).isNotNull());
    }

    @Test
    void delete_question_does_not_delete_scores_or_periods() {
        Question q = newQuestion("Café", 0, 0);
        q.getScores().addAll(List.of(s4, s5));
        q.getPeriods().add(p1);
        q = questionDao.saveAndFlush(q);

        Long scoreCountBefore = scoreDao.count();
        Long periodCountBefore = periodDao.count();

        questionDao.delete(q);
        questionDao.flush();

        assertThat(questionDao.findById(q.getId())).isNotPresent();
        // ManyToMany par défaut ne cascade pas le remove sur la cible
        assertThat(scoreDao.count()).isEqualTo(scoreCountBefore);
        assertThat(periodDao.count()).isEqualTo(periodCountBefore);
    }
}
