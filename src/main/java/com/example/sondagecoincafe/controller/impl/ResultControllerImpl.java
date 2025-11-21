package com.example.sondagecoincafe.controller.impl;

import com.example.sondagecoincafe.bll.*;
import com.example.sondagecoincafe.bo.Period;
import com.example.sondagecoincafe.bo.Question;
import com.example.sondagecoincafe.bo.Score;
import com.example.sondagecoincafe.controller.ResultController;
import com.example.sondagecoincafe.dto.ResultsDto;
import com.example.sondagecoincafe.dto.SurveyAdviceResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
public class ResultControllerImpl implements ResultController {

    private final QuestionService questionService;
    private final PeriodService periodService;
    private final ScoreService scoreService;
    private final ResultDtoService resultDtoService;
    private final SurveyAdviceService surveyAdviceService;

    public ResultControllerImpl(QuestionService questionService, ResultDtoService resultDtoService,
                                ScoreService scoreService, PeriodService periodService, SurveyAdviceService service) {

        this.questionService = questionService;
        this.resultDtoService = resultDtoService;
        this.periodService = periodService;
        this.scoreService = scoreService;
        this.surveyAdviceService = service;
    }

    @GetMapping("/results")
    @Override
    public String updateResults(Model model){
        periodService.deletePeriodsOlderThan12Months();

        List < Score > scores = scoreService.findAllScores();
        List < Period> periods = periodService.findAllPeriodes();
        List<Question> results = questionService.getDtoResults();

        float weightedGlobalRating = scoreService.getWeightedGlobalRating(scores);

        Map <Integer, Integer> mapForPieCount = questionService.getListVotesWithScore(results);

        List <String> listOfMonths = periodService.getListOfMonths(periods);
        List <Double> listOfAverageScore = periodService.getAverageScorePerMonth(periods);

        List <String> listOfTags = questionService.getTagsFromQuestionList(results);
        List <Double> listOfAverages = questionService.calculateAverageByTag(results);

        ResultsDto resultsDto = resultDtoService.fillResultsDto(weightedGlobalRating, mapForPieCount,
                                                                listOfMonths, listOfAverageScore,
                                                                listOfTags, listOfAverages);

        SurveyAdviceResponse iaAdvices = surveyAdviceService.fetchSurveyAdvice(resultsDto);
        model.addAttribute("advices", iaAdvices);

        model.addAttribute("resultsDto", resultsDto);
        return "results";
    }
}