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
    public Map<String, Integer> convertMapStringStringToStringInteger(Map<String, String> params) {
        return params.entrySet().stream()
                .filter(e -> !"_csrf".equals(e.getKey())) // on ignore seulement le token CSRF
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

        // Questions
        questionService.checkAndAddQuestionsIfNotPresent(questionCategoryMap, questions);

        // réactualisation de la liste au cas où les questions n'existeraient pas
        questions = questionDao.findAll();

        for (Question question : questions){
            questionService.fillTotalsAndTagForQuestion(question, questionsScore);
        }


//            // Score
//            Score score =scoreService.incrementTotalForScore (scoreQuestionSearched);
//            scoreDao.save(score);
//
//            // Periode, le résultat par question n'est pas traité si la personne à repondu Non Concerné
//            // cad un résultat =0.
//            if (scoreQuestionSearched >0){
//                Period currentPeriod = periodService.incrementTotalsPeriode(scoreQuestionSearched);
//                periodDao.save(currentPeriod);
//            }
    }
}

