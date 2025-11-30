package com.example.sondagecoincafe.bll.impl;

import com.example.sondagecoincafe.bll.*;
import com.example.sondagecoincafe.bo.Period;
import com.example.sondagecoincafe.bo.Question;
import com.example.sondagecoincafe.bo.Score;
import com.example.sondagecoincafe.dto.ResultsDto;
import com.example.sondagecoincafe.dto.SurveyAdviceResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Getter
@Setter
public class ResultDtoServiceImpl implements ResultDtoService {

    private final QuestionService questionService;
    private final PeriodService periodService;
    private final ScoreService scoreService;
    private final SurveyAdviceService surveyAdviceService;


    public ResultDtoServiceImpl(QuestionService questionService, PeriodService periodService,
                                ScoreService scoreService, SurveyAdviceService surveyAdviceService) {
        this.questionService = questionService;
        this.periodService = periodService;
        this.scoreService = scoreService;
        this.surveyAdviceService = surveyAdviceService;
    }

    @Override
    public List<Question> getResults(){
        return questionService.getDtoResults();
    }

    @Override
    //    TO DO : Décommenter et changer méthode quand le bouchon ne sera plus nécessaire et supprimer service mock
    public SurveyAdviceResponse getIaAdvices(List<Question> results){
        //        SurveyAdviceResponse iaAdvices = surveyAdviceService.fetchSurveyAdvice(resultsDto);
        return surveyAdviceService.processIaAdvice(results);
    }

    @Override
    public ResultsDto processResultsDisplay(List<Question> results){
        periodService.deletePeriodsOlderThan12Months();

        List <Score> scores = scoreService.findAllScores();
        List <Period> periods = periodService.findAllPeriodes();

        float weightedGlobalRating = scoreService.getWeightedGlobalRating(scores);

        Map <Integer, Integer> mapForPieCount = questionService.getListVotesWithScore(results);

        List <String> listOfMonths = periodService.getListOfMonths(periods);
        List <Double> listOfAverageScore = periodService.getAverageScorePerMonth(periods);

        List <String> listOfTags = questionService.getTagsFromQuestionList(results);
        List <Double> listOfAverages = questionService.calculateAverageByTag(results);

        return fillResultsDto(weightedGlobalRating, mapForPieCount,
                listOfMonths, listOfAverageScore,
                listOfTags, listOfAverages);
    }

    @Override
    public ResultsDto fillResultsDto(float averageGlobalRating, Map < Integer, Integer > mapForPieCount,
                                     List <String> listOfMonths, List <Double> listOfAverageScore,
                                      List <String> listOfTags, List < Double> listOfAveragesByTag)
    {
        ResultsDto resultsDto = new ResultsDto();
        resultsDto.setGlobalRating(averageGlobalRating);

        resultsDto.setPieCounts(mapForPieCount);

        ResultsDto.Timeline timeline = new ResultsDto.Timeline();
        timeline.setLabels(listOfMonths);
        timeline.setValues(listOfAverageScore);
        resultsDto.setTimeline(timeline);

        ResultsDto.Question byQuestion = new ResultsDto.Question();
        byQuestion.setLabels(listOfTags);
        byQuestion.setValues(listOfAveragesByTag);
        resultsDto.setByQuestion(byQuestion);

        return resultsDto;
    }
}
