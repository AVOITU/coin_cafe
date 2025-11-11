package com.example.sondagecoincafe.bll;

import com.example.sondagecoincafe.bo.Question;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface QuestionService {

    List<Question> getDtoResults();

    float calculateAverageRating(List<Question> results, int totalVoteCount);

    Map<Integer, Integer> getListVotesWithScore(List<Question> results);


}