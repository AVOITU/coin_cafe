package com.example.sondagecoincafe.dal;

import com.example.sondagecoincafe.bo.Period;
import com.example.sondagecoincafe.bo.Question;
import com.example.sondagecoincafe.bo.Score;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

//Tester avec application.properties en fixant spring.jpa.hibernate.ddl-auto=none
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class QuestionDaoTest {

    @Autowired QuestionDao questionDao;
    @Autowired ScoreDao scoreDao;
    @Autowired PeriodDao periodDao;


    @BeforeEach
    void init() {
        questionDao.deleteAll();
        scoreDao.deleteAll();
        periodDao.deleteAll();
    }
    @Test
    void save_and_findAll_with_relations_ok() {
        // scores 0..5
        List<Score> scores = scoreDao.saveAll(
                List.of(s(0), s(1), s(2), s(3), s(4), s(5))
        );
        // periods
        Period p1 = new Period(); p1.setPeriodTotalVotes(new BigDecimal("3.40"));
        Period p2 = new Period(); p2.setPeriodTotalVotes(new BigDecimal("4.10"));
        periodDao.saveAll(List.of(p1, p2));

        // question
        Question q = new Question();
        q.setQuestionText("Hygiène");
        q.setQuestionTotalVotes(0);
        q.setAllVotesCount(0);
        q.getScores().addAll(Set.of(scores.get(3), scores.get(4), scores.get(5)));
        q.getPeriods().addAll(Set.of(p1, p2));
        questionDao.save(q);

        List<Question> all = questionDao.findAll();
        assertThat(all).hasSize(1);
        Question saved = all.get(0);
        assertThat(saved.getScores()).hasSize(3);
        assertThat(saved.getPeriods()).hasSize(2);
    }

    private Score s(int v){ var s=new Score(); s.setScore(v); s.setScoreVoteCount(0); return s; }
}