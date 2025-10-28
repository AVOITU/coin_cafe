package com.example.sondagecoincafe.bll.impl;

import com.example.sondagecoincafe.bll.ResultService;
import com.example.sondagecoincafe.dto.ResultsDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ResultServiceImpl implements ResultService {

    @Override
    public ResultsDto getResultsDto() {
        ResultsDto resultsDto = new ResultsDto();
        resultsDto.setGlobalRating(3.8);

        resultsDto.setPieCounts(Map.of(1, 8, 2, 12, 3, 22, 4, 28, 5, 30));

        ResultsDto.Timeline t = new ResultsDto.Timeline();
        t.setLabels(List.of("mois 1", "mois 2", "mois 3", "mois 4", "mois 5"));
        t.setValues(List.of(1.9, 2.7, 2.3, 3.1, 3.4));
        resultsDto.setTimeline(t);

        // idem pour categories et byQuestion...
        return resultsDto;
    }
}
