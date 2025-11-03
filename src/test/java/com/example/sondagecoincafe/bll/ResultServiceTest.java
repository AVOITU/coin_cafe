package com.example.sondagecoincafe.bll;

import com.example.sondagecoincafe.bll.impl.ResultServiceImpl;
import com.example.sondagecoincafe.bo.Question;
import com.example.sondagecoincafe.dal.ResultDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
class ResultServiceImplTest {

    @Mock
    private ResultDao resultDao;

    @InjectMocks
    private ResultServiceImpl service;

    private Question result(String name, float note, int votes) {
        Question r = new Question(name);
        r.setQuestionGlobalNotation(note);
        r.setTotalVoteCount(votes);
        return r;
    }

    @Test
    void getDtoResults_should_return_all_results_from_dao() {
        List<Question> expected = List.of(result("Q1", 4f, 10), result("Q2", 3f, 5));
        when(resultDao.findAllResults()).thenReturn(expected);

        List<Question> out = service.getDtoResults();

        assertThat(out).hasSize(2);
        verify(resultDao).findAllResults();
    }

    @Test
    void calculateAverageRating_should_return_correct_average() {
        List<Question> data = List.of(result("Q1", 4f, 10), result("Q2", 2f, 5));

        float avg = service.calculateAverageRating(data);

        assertThat(avg).isEqualTo(3.0f);
    }

    @Test
    void calculateAverageRating_should_return_0_if_empty_list() {
        float avg = service.calculateAverageRating(List.of());
        assertThat(avg).isZero();
    }

    @Test
    void getTotalVoteCounts_should_return_question_names() {
        List<Question> data = List.of(result("Q1", 4f, 10), result("Q2", 2f, 5));

        List<String> names = service.getTotalVoteCounts(data);

        assertThat(names).containsExactly("Q1", "Q2");
    }

    @Test
    void getQuestionGlobalNotations_should_return_notations() {
        List<Question> data = List.of(result("Q1", 4f, 10), result("Q2", 2f, 5));

        List<Float> notes = service.getQuestionGlobalNotations(data);

        assertThat(notes).containsExactly(4f, 2f);
    }
}
