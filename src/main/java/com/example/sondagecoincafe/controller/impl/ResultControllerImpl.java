package com.example.sondagecoincafe.controller.impl;

import com.example.sondagecoincafe.bll.ResultDtoService;
import com.example.sondagecoincafe.controller.ResultController;
import com.example.sondagecoincafe.dto.ResultsDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ResultControllerImpl implements ResultController {

    private final ResultDtoService resultDtoService;

    public ResultControllerImpl(ResultDtoService resultDtoService) {
        this.resultDtoService = resultDtoService;
    }

//    TODO : decommenter le code quand la BDD sera accessible
    @GetMapping("/results")
    public String updateResults(Model m){
        ResultsDto resultsDto = resultDtoService.fillResultsDto();
        m.addAttribute("resultsDto", resultsDto);
        return "results";
    }
}