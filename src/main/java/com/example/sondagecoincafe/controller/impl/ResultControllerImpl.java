package com.example.sondagecoincafe.controller.impl;

import com.example.sondagecoincafe.bll.NoteService;
import com.example.sondagecoincafe.bll.PeriodService;
import com.example.sondagecoincafe.bll.ResultDtoService;
import com.example.sondagecoincafe.bll.QuestionService;
import com.example.sondagecoincafe.bo.Question;
import com.example.sondagecoincafe.controller.ResultController;
import com.example.sondagecoincafe.dto.ResultsDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
public class ResultControllerImpl implements ResultController {

    private final QuestionService questionService;
    private final PeriodService periodService;
    private final ResultDtoService resultDtoService;

    public ResultControllerImpl(QuestionService questionService, ResultDtoService resultDtoService, NoteService noteService, PeriodService periodService) {
        this.questionService = questionService;
        this.resultDtoService = resultDtoService;
        this.periodService = periodService;
    }

//    TODO : décommenter le code quand la BDD sera accessible
    @GetMapping("/results")
    public String updateResults(Model m){
        List<Question> results = questionService.getDtoResults();

        int totalVoteCounts = periodService.calculateTotalVotes(results);
        float averageGlobalRating = questionService.calculateAverageRating(results, totalVoteCounts);

        Map <Integer , Integer> mapForPieCount = questionService.getListVotesWithScore(results);

        ResultsDto resultsDto = resultDtoService.fillResultsDto(averageGlobalRating, mapForPieCount);
        m.addAttribute("resultsDto", resultsDto);
        return "results";
    }
}