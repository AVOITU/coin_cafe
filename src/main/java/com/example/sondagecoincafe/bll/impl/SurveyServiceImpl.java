package com.example.sondagecoincafe.bll.impl;

import com.example.sondagecoincafe.bll.PeriodService;
import com.example.sondagecoincafe.bll.QuestionService;
import com.example.sondagecoincafe.bll.ScoreService;
import com.example.sondagecoincafe.bll.SurveyService;
import com.example.sondagecoincafe.bo.Period;
import com.example.sondagecoincafe.bo.Question;
import com.example.sondagecoincafe.bo.Score;
import com.example.sondagecoincafe.configuration.AppConstants;
import com.example.sondagecoincafe.dal.PeriodDao;
import com.example.sondagecoincafe.dal.QuestionDao;
import com.example.sondagecoincafe.dal.ScoreDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Transactional
    public void processSurvey(Map<String, Integer> questionsScore) {

        List<Question> questions = questionDao.findAll();
        int totalIncrementedScore = 0;

        for (Question question : questions) {
            String searchedQuestion = question.getQuestionText();
            int scoreQuestionSearched = questionsScore.get(searchedQuestion);

            question = questionService.fillTotalsAndTagForQuestion(questionsScore, scoreQuestionSearched,
                                                                    question, searchedQuestion);
            questionDao.save(question);

            Score score =scoreService.incrementTotalForScore (scoreQuestionSearched);
            scoreDao.save(score);
        }

        for (Integer value : questionsScore.values()) {
            totalIncrementedScore += value;
        }

        Period currentPeriod = periodService.incrementTotalsPeriode(totalIncrementedScore);
        periodDao.save(currentPeriod);

    }
}

