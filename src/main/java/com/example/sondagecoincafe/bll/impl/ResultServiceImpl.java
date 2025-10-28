package com.example.sondagecoincafe.bll.impl;

import com.example.sondagecoincafe.dto.ResultsDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ResultServiceImpl {

    public ResultsDto getSurvey() {
        ResultsDto resultsDto = new ResultsDto();
        resultsDto.setGlobalRating(3.8);

        resultsDto.setPieCounts(Map.of(1, 8, 2, 12, 3, 22, 4, 28, 5, 30));

        ResultsDto.Timeline t = new ResultsDto.Timeline();
        t.setLabels(List.of("Semaine 1", "Semaine 2", "Semaine 3", "Semaine 4", "Semaine 5"));
        t.setValues(List.of(1.9, 2.7, 2.3, 3.1, 3.4));
        resultsDto.setTimeline(t);

        // idem pour categories et byQuestion...
        return resultsDto;
    }
}
