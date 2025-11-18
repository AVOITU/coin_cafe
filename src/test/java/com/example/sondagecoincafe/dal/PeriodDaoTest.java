package com.example.sondagecoincafe.dal;

import com.example.sondagecoincafe.bo.Period;
import com.example.sondagecoincafe.bo.Question;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

//Tester avec application.properties en fixant spring.jpa.hibernate.ddl-auto=none
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class PeriodDaoTest {

    @Autowired PeriodDao periodDao;
    @Autowired QuestionDao questionDao;
    @Autowired EntityManager em;

    private Period p1, p2;

    @BeforeEach
    void setUp() {
        periodDao.deleteAll();
        questionDao.deleteAll();
        p1 = periodDao.save(newPeriod(340));
        p2 = periodDao.save(newPeriod(410));
    }

    @Test
    void save_and_findAll_ok() {
        List<Period> all = periodDao.findAll();

        assertThat(all).isNotEmpty();
        assertThat(all).hasSizeGreaterThanOrEqualTo(2);
        assertThat(all).extracting("periodTotalVotes")
                .contains(new BigDecimal("3.40"), new BigDecimal("4.10"));

        assertThat(all)
                .allSatisfy(p -> assertThat(p.getTimestampPeriod()).isNotNull());
    }

    @Test
    void findById_returns_present_and_absent() {
        Optional<Period> found = periodDao.findById(p1.getId());
        Optional<Period> absent = periodDao.findById(-999L);

        assertThat(found).isPresent();
        assertThat(found.get().getPeriodTotalVotes()).isEqualTo(340);
        assertThat(absent).isNotPresent();
    }

    @Test
    void update_totalVotes_persists_changes() {
        p1.setPeriodTotalVotes(485);
        Period updated = periodDao.saveAndFlush(p1);

        assertThat(updated.getPeriodTotalVotes()).isEqualTo(485);
        assertThat(updated.getTimestampPeriod()).isNotNull();
    }

    @Test
    void timestampPeriod_is_set_automatically_on_insert() {
        Period p = newPeriod(270);
        periodDao.saveAndFlush(p);

        Period reloaded = periodDao.findById(p.getId()).orElseThrow();
        assertThat(reloaded.getTimestampPeriod()).isNotNull();
    }

    @Test
    void link_question_and_verify_lazy_loading() {
        Question q = new Question();
        q.setQuestionText("Ambiance");
        q.setQuestionTotalVotes(0);
        q.setQuestionTotalScore(0);

        q.getPeriods().addAll(List.of(p1, p2));
        questionDao.saveAndFlush(q);

        //        IMPORTANT: l'entity manager recharge depuis la BDD
        em.clear();

        Period reloaded = periodDao.findById(p1.getId()).orElseThrow();
        assertThat(reloaded.getQuestions()).isNotEmpty();
        assertThat(reloaded.getQuestions())
                .extracting("questionText")
                .contains("Ambiance");
    }

//    TO DO : Oblige la modification de la BO à voir plus tard
//    @Test
//    void delete_period_does_not_delete_questions() {
//        // given
//        Question q = new Question();
//        q.setQuestionText("Hygiène");
//        q.setQuestionTotalVotes(0);
//        q.setAllVotesCount(0);
//        q.getPeriods().add(p1);
//        q = questionDao.saveAndFlush(q);
//
//        Long qId = q.getId();
//        Long p1Id = p1.getId();
//        long countBefore = questionDao.count();
//
//        // break link on owning side
//        Question qReloaded = questionDao.findById(qId).orElseThrow();
//        qReloaded.getPeriods().removeIf(p -> p.getId().equals(p1Id));
//        questionDao.saveAndFlush(qReloaded); // supprime la ligne de jointure
//
//        // then delete period
//        periodDao.deleteById(p1Id);
//        periodDao.flush();
//
//        assertThat(periodDao.findById(p1Id)).isNotPresent();
//        assertThat(questionDao.count()).isEqualTo(countBefore);
//        assertThat(questionDao.findById(qId)).isPresent();
//    }

    // ---------- helpers ----------
    private Period newPeriod(int totalVotes) {
        Period p = new Period();
        p.setPeriodTotalVotes(totalVotes);
        return p;
    }
}