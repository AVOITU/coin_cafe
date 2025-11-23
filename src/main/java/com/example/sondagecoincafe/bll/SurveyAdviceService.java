package com.example.sondagecoincafe.bll;

import com.example.sondagecoincafe.bo.Question;
import com.example.sondagecoincafe.dto.CategoryAdviceInput;
import com.example.sondagecoincafe.dto.ResultsDto;
import com.example.sondagecoincafe.dto.SurveyAdviceResponse;

import java.util.List;

public interface SurveyAdviceService {
//    SurveyAdviceResponse fetchSurveyAdvice(ResultsDto resultsDto);

    SurveyAdviceResponse fetchSurveyAdvice(ResultsDto dto);

    List<CategoryAdviceInput> buildCategoryInputs(ResultsDto dto);

    SurveyAdviceResponse fetchSurveyAdviceMock();

    boolean consultIaForActualMonth();

    //    TO DO : replace by fetchSurvey quand plus mock
    SurveyAdviceResponse processIaAdvice(List<Question> questions);

    SurveyAdviceResponse buildSurveyAdviceFromDb(List<Question> questions);
}