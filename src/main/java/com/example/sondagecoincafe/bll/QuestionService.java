package com.example.sondagecoincafe.bll;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface QuestionService {

    List<Question> getDtoResults();
    float calculateAverageRating(List<Question> results);
    List<String> getTotalVoteCounts(List<Question> results);
    public List<Float> getQuestionGlobalNotations(List<Question> results);
}