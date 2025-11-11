package com.example.sondagecoincafe.bll.impl;

import com.example.sondagecoincafe.bll.ResultDtoService;
import com.example.sondagecoincafe.dto.ResultsDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Getter
@Setter
@NoArgsConstructor
public class ResultDtoServiceImpl implements ResultDtoService {

    @Override
    public ResultsDto fillResultsDto(float averageGlobalRating, Map < Integer, Integer > mapForPieCount) {
        ResultsDto resultsDto = new ResultsDto();
        resultsDto.setGlobalRating(averageGlobalRating);

        resultsDto.setPieCounts(mapForPieCount);

        ResultsDto.Timeline timeline = new ResultsDto.Timeline();
        timeline.setLabels(List.of("mois 1", "mois 2", "mois 3", "mois 4", "mois 5"));
        timeline.setValues(List.of(1.9, 2.7, 2.3, 3.1, 3.4));
        resultsDto.setTimeline(timeline);

        ResultsDto.Question byQuestion = new ResultsDto.Question();
        byQuestion.setLabels(List.of("Hygiène","Accueil","Ambiance","Accessibilité",
                "Signalétique","Service","Produits","Qualité/Prix","Diversité","Wi‑Fi"));
        byQuestion.setValues(List.of(3.9,4.2,3.7,3.8,3.4,4.1,3.6,3.8,3.2,3.5));
        resultsDto.setByQuestion(byQuestion);

        return resultsDto;
    }
}
