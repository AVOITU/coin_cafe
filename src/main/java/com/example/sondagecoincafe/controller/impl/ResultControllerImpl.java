package com.example.sondagecoincafe.controller.impl;

import com.example.sondagecoincafe.bll.NoteService;
import com.example.sondagecoincafe.bll.ResultDtoService;
import com.example.sondagecoincafe.bll.QuestionService;
import com.example.sondagecoincafe.controller.ResultController;
import com.example.sondagecoincafe.dto.ResultsDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ResultControllerImpl implements ResultController {

    private final QuestionService questionService;
    private final ResultDtoService resultDtoService;

    public ResultControllerImpl(QuestionService questionService, ResultDtoService resultDtoService, NoteService noteService) {
        this.questionService = questionService;
        this.resultDtoService = resultDtoService;
    }

//    TODO : décommenter le code quand la BDD sera accessible
    @GetMapping("/results")
    public String updateResults(Model m){
//        List<Question> results = questionService.getDtoResults();
//        float averageGlobalRating = questionService.calculateAverageRating(results);
//        List<String> totalVoteCounts = questionService.getTotalVoteCounts(results);
//        List<Float> questionGlobalNotations = questionService.getQuestionGlobalNotations(results);

        ResultsDto resultsDto = resultDtoService.fillResultsDto();
        m.addAttribute("resultsDto", resultsDto);
        return "results";
    }
}