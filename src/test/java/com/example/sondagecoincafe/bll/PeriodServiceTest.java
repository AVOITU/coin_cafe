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
                p(2025, 1, 1),  // JANUARY
                p(2025, 3, 1),  // MARCH
                p(2025, 2, 1),  // FEBRUARY
                p(2025, 5, 1),  // MAY
                p(2025, 4, 1),  // APRIL
                p(2025, 6, 1)   // JUNE (doit être ignoré, on ne garde que 5)
        );

        List<String> out = periodService.getListOfMonths(in);

        assertThat(out).hasSize(5);
        // ordre = ordre d’itération d’entrée
        assertThat(out).containsExactly("JANUARY","MARCH","FEBRUARY","MAY","APRIL");
    }

    @Test
    void returns_all_when_less_than_five() {
        List<Period> in = List.of(p(2025, 9, 1), p(2025, 10, 1));

        List<String> out = periodService.getListOfMonths(in);

        assertThat(out).hasSize(2)
                .containsExactly("SEPTEMBER","OCTOBER");
    }

    // ---------- helper ----------
    private static Period p(int y, int m, int d) {
        Period p = new Period();
        p.setTimestampPeriod(LocalDateTime.of(y, m, d, 0, 0));
        return p;
    }
}
