package com.example.sondagecoincafe.bll;

import com.example.sondagecoincafe.bo.Question;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface QuestionService {

    List<Question> getDtoResults();

    Map<Integer, Integer> getListVotesWithScore(List<Question> results);

    List <String> getTagsFromQuestionList(List <Question> questions);

    List <Double> calculateAverageByTag(List <Question> questions);
}