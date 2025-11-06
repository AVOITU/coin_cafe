package com.example.sondagecoincafe.dal;

import com.example.sondagecoincafe.bo.Period;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PeriodDaoTest {

    @Autowired PeriodDao periodDao;

    @BeforeEach
    void init() {
        periodDao.deleteAll();
    }

    @Test
    void creationTimestamp_populated() {
        Period p = new Period();
        p.setPeriodTotalVotes(new BigDecimal("2.50"));
        Period saved = periodDao.saveAndFlush(p);
        assertThat(saved.getTimestampPeriod()).isNotNull();
    }
}