package com.example.sondagecoincafe.controller.impl;

import com.example.sondagecoincafe.bll.ResultService;
import com.example.sondagecoincafe.controller.ResultController;
import com.example.sondagecoincafe.dto.ResultsDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ResultControllerImpl implements ResultController {

    private final ResultService resultService;

    public ResultControllerImpl(ResultService resultService) {
        this.resultService = resultService;
    }

    @GetMapping("/results")
    public String updateResults(Model m){
        ResultsDto resultsDto = resultService.getResultsDto();
        m.addAttribute("resultsDto", resultsDto);
        return "results";
    }
}