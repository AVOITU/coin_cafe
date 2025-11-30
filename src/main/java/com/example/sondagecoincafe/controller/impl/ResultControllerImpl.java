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

    private final ResultDtoService resultDtoService;

    public ResultControllerImpl(ResultDtoService resultDtoService) {
        this.resultDtoService = resultDtoService;
    }

    @GetMapping("/results")
    @Override
    public String updateResults(Model model){

        List<Question> results = resultDtoService.getResults();
        ResultsDto resultsDto = resultDtoService.processResultsDisplay(results);
        SurveyAdviceResponse iaAdvices = resultDtoService.getIaAdvices(results);

        model.addAttribute("advices", iaAdvices.getResults());
        model.addAttribute("resultsDto", resultsDto);
        return "results";
    }
}