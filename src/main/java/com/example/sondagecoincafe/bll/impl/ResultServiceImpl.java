package com.example.sondagecoincafe.bll.impl;

import com.example.sondagecoincafe.bll.ResultService;
import com.example.sondagecoincafe.bo.Question;
import com.example.sondagecoincafe.dal.ResultDao;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Getter @Setter
@NoArgsConstructor
public class ResultServiceImpl implements ResultService {

    private ResultDao resultDao;

    public ResultServiceImpl(ResultDao resultDao){
        this.resultDao = resultDao;
    }

    @Override
    public List<Question> getDtoResults() {
        return resultDao.findAllResults();
    }

    @Override
    public float calculateAverageRating(List<Question> results){
        float averageGlobalRating = (float) results.stream()
                .mapToDouble(Question::getQuestionGlobalNotation) // <- extrais le float par resultat remonté
                .average()
                .orElse(0);
        return averageGlobalRating;
    }

    @Override
    public List<String> getTotalVoteCounts(List<Question> results){
        List<String> totalVoteCounts = results.stream()
                .map(Question::getQuestionName)
                .toList();
        return totalVoteCounts;
    }

    @Override
    public List<Float> getQuestionGlobalNotations(List<Question> results){
        List<Float> questionGlobalNotations = results.stream()
                .map(Question::getQuestionGlobalNotation)
                .toList();
        return questionGlobalNotations;
    }
}
