package com.example.sondagecoincafe.bll;


import com.example.sondagecoincafe.bo.Period;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class GetFirstFiveMonthsTest {

    @Autowired
    private PeriodService periodService;

    @Test
    void returns_empty_list_when_input_is_null() {
        assertThat(periodService.getListOfMonths(null)).isEmpty();
    }

    @Test
    void returns_empty_list_when_input_is_empty() {
        assertThat(periodService.getListOfMonths(List.of())).isEmpty();
    }

    @Test
    void returns_first_five_months_in_order() {
        List<Period> in = List.of(
                p(1),  // JANUARY
                p(3),  // MARCH
                p(2),  // FEBRUARY
                p(5),  // MAY
                p(4),  // APRIL
                p(6)   // JUNE (doit être ignoré, on ne garde que 5)
        );

        List<String> out = periodService.getListOfMonths(in);

        assertThat(out).hasSize(5);
        // ordre = ordre d’itération d’entrée
        assertThat(out).containsExactly("JANVIER","MARS","FÉVRIER","MAI","AVRIL");
    }

    @Test
    void returns_all_when_less_than_five() {
        List<Period> in = List.of(p(9), p(10));

        List<String> out = periodService.getListOfMonths(in);

        assertThat(out).hasSize(2)
                .containsExactly("SEPTEMBRE","OCTOBRE");
    }

    // ---------- helper ----------
    private static Period p(int m) {
        Period p = new Period();
        p.setTimestampPeriod(LocalDateTime.of(2025, m, 1, 0, 0));
        return p;
    }
}
