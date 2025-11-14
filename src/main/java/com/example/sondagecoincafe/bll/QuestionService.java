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

    List < Question> findAllQuestion ();

    Question fillTotalsAndTagForQuestion(Map<String, Integer> questionsScore, int scoreQuestionSearched,
                                         Question question, String searchedQuestion,
                                         Map<String, String> questionCategoryMap);

    Map<String, String> buildQuestionCategoryMap();
}