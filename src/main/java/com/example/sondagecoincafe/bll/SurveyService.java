package com.example.sondagecoincafe.bll;

import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

public interface SurveyService {

    Map<String, Integer> convertMapStringStringToStringInteger(Map<String, String> params);

    @Transactional
    void processSurvey(Map<String, Integer> questionsScore);
}
