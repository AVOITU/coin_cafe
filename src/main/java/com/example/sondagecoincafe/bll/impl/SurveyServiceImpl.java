package com.example.sondagecoincafe.bll.impl;

import com.example.sondagecoincafe.bll.PeriodService;
import com.example.sondagecoincafe.bll.QuestionService;
import com.example.sondagecoincafe.bll.ScoreService;
import com.example.sondagecoincafe.bll.SurveyService;
import com.example.sondagecoincafe.bo.Period;
import com.example.sondagecoincafe.bo.Question;
import com.example.sondagecoincafe.dal.PeriodDao;
import com.example.sondagecoincafe.dal.QuestionDao;
import com.example.sondagecoincafe.dal.ScoreDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SurveyServiceImpl implements SurveyService {


    private final QuestionDao questionDao;

    private final PeriodService periodService;
    private final ScoreService scoreService;
    private final QuestionService questionService;

    public SurveyServiceImpl(QuestionDao questionDao, ScoreDao scoreDao, PeriodDao periodDao, PeriodService periodService, ScoreService scoreService, QuestionService questionService) {
        this.questionDao = questionDao;
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
    public void processSurvey(Map<String, Integer> formResponses) {

        List<Question> questions = questionDao.findAll();

        Map<String, String> questionCategoryMap = questionService.buildQuestionCategoryMap();

        // fixe au 1 du mois afin de comparer les périodes par la suite
        LocalDateTime firstDayOfMonth = LocalDateTime.now()
                .withDayOfMonth(1)
                .withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);

        Period currentPeriod = periodService.getOrCreateCurrentPeriodByTimestamp(firstDayOfMonth);

        // Questions
        questionService.checkAndAddQuestionsIfNotPresent(questionCategoryMap, questions);

        // réactualisation de la liste au cas où les questions n'existeraient pas
        questions = questionDao.findAll();

        for (Question question : questions){

            int responseScore = formResponses.get(question.getQuestionText());

            // Score
            scoreService.incrementAndSaveTotalForScore (question, responseScore);

            // Periodes et Questions, le résultat n'est pas traité si la personne à repondu Non Concerné
            // cad un résultat =0.
            if (responseScore >0){
                questionService.fillAndSaveTotalsForQuestion(question, responseScore);
                periodService.incrementAndSaveTotalsPeriod(responseScore, currentPeriod);
            }
        }
    }
}

