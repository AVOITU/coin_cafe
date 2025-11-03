package com.example.sondagecoincafe.controller.impl;

import com.example.sondagecoincafe.bll.NoteService;
import com.example.sondagecoincafe.bll.ResultDtoService;
import com.example.sondagecoincafe.bll.ResultService;
import com.example.sondagecoincafe.controller.ResultController;
import com.example.sondagecoincafe.dto.ResultsDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ResultControllerImpl implements ResultController {

    private final ResultService resultService;
    private final ResultDtoService resultDtoService;
    private final NoteService noteService;

    public ResultControllerImpl(ResultService resultService, ResultDtoService resultDtoService, NoteService noteService) {
        this.resultService = resultService;
        this.resultDtoService = resultDtoService;
        this.noteService = noteService;
    }

//    TODO : décommenter le code quand la BDD sera accessible
    @GetMapping("/results")
    public String updateResults(Model m){
//        List<Result> results = resultService.getDtoResults();
//        float averageGlobalRating = resultService.calculateAverageRating(results);
//        List<String> totalVoteCounts = resultService.getTotalVoteCounts(results);
//        List<Float> questionGlobalNotations = resultService.getQuestionGlobalNotations(results);

//        List <Note> notesList = noteService.findAllVoteCount();
//        List <Integer> notes = noteService.getVoteCounts(notesList);

        ResultsDto resultsDto = resultDtoService.fillResultsDto();
        m.addAttribute("resultsDto", resultsDto);
        return "results";
    }
}