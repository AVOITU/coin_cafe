package com.example.sondagecoincafe.bll.impl;

import com.example.sondagecoincafe.bll.PeriodService;
import com.example.sondagecoincafe.bll.QuestionService;
import com.example.sondagecoincafe.bll.ScoreService;
import com.example.sondagecoincafe.bll.SurveyService;
import com.example.sondagecoincafe.bo.Period;
import com.example.sondagecoincafe.bo.Question;
import com.example.sondagecoincafe.bo.Score;
import com.example.sondagecoincafe.dal.PeriodDao;
import com.example.sondagecoincafe.dal.QuestionDao;
import com.example.sondagecoincafe.dal.ScoreDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SurveyServiceImpl implements SurveyService {


    private final QuestionDao questionDao;
    private final ScoreDao scoreDao;
    private final PeriodDao periodDao;

    private final PeriodService periodService;
    private final ScoreService scoreService;
    private final QuestionService questionService;

    public SurveyServiceImpl(QuestionDao questionDao, ScoreDao scoreDao, PeriodDao periodDao, PeriodService periodService, ScoreService scoreService, QuestionService questionService) {
        this.questionDao = questionDao;
        this.scoreDao = scoreDao;
        this.periodDao = periodDao;
        this.periodService = periodService;
        this.scoreService = scoreService;
        this.questionService = questionService;
    }

    @Override
    public Map<String, Integer> convertMapStringStringToStringInteger(Map<String, String> params){
        return  params.entrySet().stream()
                .filter(e -> e.getKey().startsWith("q"))          // ignore _csrf
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> Integer.parseInt(e.getValue())
                ));
    }

    @Transactional
    @Override
    public void processSurvey(Map<String, Integer> questionsScore) {

        List<Question> questions = questionDao.findAll();

        Map<String, String> questionCategoryMap = questionService.buildQuestionCategoryMap();

//        for (String searchedQuestion : questionsScore.keySet()) {
//            int scoreQuestionSearched = questionsScore.get(searchedQuestion);
//
//            // Questions
//            int questionIndex = 0;
//            for (Question question : questions) {
//                question = questionService.fillTotalsAndTagForQuestion(questionsScore, scoreQuestionSearched,
//                        question, searchedQuestion, questionCategoryMap, questionIndex);
//                questionDao.save(question);
//                questionIndex += 1;
//        }
//
//            // Score
//            Score score =scoreService.incrementTotalForScore (scoreQuestionSearched);
//            scoreDao.save(score);
//
//            // Periode, le résultat par question n'est pas traité si la persone à repondu Non Concerné
//            // cad un résultat =0.
//            if (scoreQuestionSearched >0){
//                Period currentPeriod = periodService.incrementTotalsPeriode(scoreQuestionSearched);
//                periodDao.save(currentPeriod);
//            }
//        }
    }
}

