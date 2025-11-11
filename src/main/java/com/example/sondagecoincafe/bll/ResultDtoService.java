package com.example.sondagecoincafe.bll;

import com.example.sondagecoincafe.dto.ResultsDto;

import java.util.List;
import java.util.Map;

public interface ResultDtoService {

    ResultsDto fillResultsDto(float averageGlobalRating, Map <Integer, Integer> mapForPieCount,
                              List<String> listOfMonths, List <Double> listOfAverageScore);
}
