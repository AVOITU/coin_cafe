package com.example.sondagecoincafe.bll;

//import com.example.sondagecoincafe.dto.SurveyAdviceResponse;

import com.example.sondagecoincafe.dto.CategoryAdviceInput;
import com.example.sondagecoincafe.dto.ResultsDto;
import com.example.sondagecoincafe.dto.SurveyAdviceResponse;

import java.util.List;

public interface SurveyAdviceService {
    SurveyAdviceResponse fetchSurveyAdvice(ResultsDto dto);

    List<CategoryAdviceInput> buildCategoryInputs(ResultsDto dto);

    String callPhpAdvice(ResultsDto dto);
}
