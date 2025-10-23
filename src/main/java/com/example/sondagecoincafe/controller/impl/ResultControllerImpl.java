package com.example.sondagecoincafe.controller.impl;

import com.example.sondagecoincafe.bll.ResultService;
import com.example.sondagecoincafe.controller.ResultController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
public class ResultControllerImpl implements ResultController {



    private ResultService resultService;

    public ResultControllerImpl(ResultService resultService) {
        this.resultService = resultService;
    }

//    public ResultService getSurvey() {
//        resultService.setGlobalRating(3.8);
//
//        resultService.setPieCounts(Map.of(1,8,2,12,3,22,4,28,5,30));
//
//        resultService.Timeline t = new resultService.Timeline();
//        t.setLabels(List.of("Semaine 1","Semaine 2","Semaine 3","Semaine 4","Semaine 5"));
//        t.setValues(List.of(1.9, 2.7, 2.3, 3.1, 3.4));
//        resultService.setTimeline(t);
//
//        // idem pour categories et byQuestion...
//        return resultService;
//    }

    @GetMapping("/results")
    public String updateResults(Model m){
        m.addAttribute("resultService", resultService);
        return "results";
    }

}
