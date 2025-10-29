package com.example.sondagecoincafe.controller.impl;

import com.example.sondagecoincafe.bll.ResultDtoService;
import com.example.sondagecoincafe.bll.ResultService;
import com.example.sondagecoincafe.bo.Note;
import com.example.sondagecoincafe.bo.Result;
import com.example.sondagecoincafe.controller.ResultController;
import com.example.sondagecoincafe.dto.ResultsDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ResultControllerImpl implements ResultController {

    private final ResultService resultService;
    private final ResultDtoService resultDtoService;

    public ResultControllerImpl(ResultService resultService, ResultDtoService resultDtoService) {
        this.resultService = resultService;
        this.resultDtoService = resultDtoService;
    }

    @GetMapping("/results")
    public String updateResults(Model m){
        List<Result> results = resultService.getDtoResults();
        float averageGlobalRating = resultService.calculateAverageRating(results);
        List<String> totalVoteCounts = resultService.getTotalVoteCounts(results);
        List<Float> questionGlobalNotations = resultService.getQuestionGlobalNotations(results);

        ResultsDto resultsDto = resultDtoService.fillResultsDto();
        m.addAttribute("resultsDto", resultsDto);
        return "results";
    }
}