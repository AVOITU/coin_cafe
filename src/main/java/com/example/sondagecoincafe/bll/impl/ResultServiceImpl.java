package com.example.sondagecoincafe.bll.impl;

import com.example.sondagecoincafe.bll.ResultService;
import com.example.sondagecoincafe.bo.Note;
import com.example.sondagecoincafe.bo.Periode;
import com.example.sondagecoincafe.bo.Result;
import com.example.sondagecoincafe.dal.ResultDao;
import com.example.sondagecoincafe.dto.ResultsDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Getter @Setter
@NoArgsConstructor
public class ResultServiceImpl implements ResultService {

    private ResultDao resultDao;

    public ResultServiceImpl(ResultDao resultDao){
        this.resultDao = resultDao;
    }

    @Override
    public List<Result> getDtoResults() {
        return resultDao.findAllResults();
    }

    @Override
    public float calculateAverageRating(List<Result> results){
        float averageGlobalRating = (float) results.stream()
                .mapToDouble(Result::getQuestionGlobalNotation) // <- extrais le float par resultat remonté
                .average()
                .orElse(0);
        return averageGlobalRating;
    }

    @Override
    public List<String> getTotalVoteCounts(List<Result> results){
        List<String> totalVoteCounts = results.stream()
                .map(Result::getQuestionName)
                .toList();
        return totalVoteCounts;
    }

    @Override
    public List<Float> getQuestionGlobalNotations(List<Result> results){
        List<Float> questionGlobalNotations = results.stream()
                .map(Result::getQuestionGlobalNotation)
                .toList();
        return questionGlobalNotations;
    }
}
