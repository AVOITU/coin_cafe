package com.example.sondagecoincafe.dal;

import com.example.sondagecoincafe.bo.Score;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ScoreDaoTest {

    @Autowired ScoreDao scoreDao;

    @BeforeEach
    void init() {
        scoreDao.deleteAll();
    }

    @Test
    void reject_score_out_of_range() {
        Score s = new Score();
        s.setScore(7);              // hors [0..5]
        s.setScoreVoteCount(0);
        assertThatThrownBy(() -> scoreDao.saveAndFlush(s))
                .isInstanceOf(ConstraintViolationException.class);
    }
}