package com.example.sondagecoincafe.bll;

import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

public interface SurveyService {

    @Transactional
    void processSurvey(Map<String, Integer> questionsScore);
}
