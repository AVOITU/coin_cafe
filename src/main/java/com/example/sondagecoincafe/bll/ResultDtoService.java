package com.example.sondagecoincafe.bll;

import com.example.sondagecoincafe.bo.Question;
import com.example.sondagecoincafe.dto.ResultsDto;
import com.example.sondagecoincafe.dto.SurveyAdviceResponse;

import java.util.List;
import java.util.Map;

public interface ResultDtoService {

    List<Question> getResults();

    SurveyAdviceResponse getIaAdvices(List<Question> results);

    ResultsDto processResultsDisplay(List<Question> results);

    ResultsDto fillResultsDto(float averageGlobalRating, Map <Integer, Integer> mapForPieCount,
                              List<String> listOfMonths, List <Double> listOfAverageScore,
                              List <String> listOfTags, List < Double> listOfAverages);
}
