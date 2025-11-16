package com.example.sondagecoincafe.controller.impl;

import com.example.sondagecoincafe.bll.SurveyService;
import com.example.sondagecoincafe.configuration.AppConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
public class HomeController {

    private final SurveyService surveyService;

    public HomeController(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @GetMapping("")
    public String localhostPort(){
        return "redirect:survey";
    }

    @GetMapping("/survey")
    public String displayItems(Model model) {
        Map <String, Integer> questionsScore= new HashMap<>();
        var questions = AppConstants.QUESTIONS_SENTENCES;
        model.addAttribute("questions", questions);
        model.addAttribute("questionsScore", questionsScore);
        return "survey";
    }


    @PostMapping("/survey")
    public String handleSurveySubmit(@RequestParam Map <String, String> params, Model model) {

//        par défaut les paramètres du formulaire sont transmis sous forme de string donc il faut convertir les scores
        Map<String, Integer> questionsScore = surveyService.convertMapStringStringToStringInteger(params);
        surveyService.processSurvey(questionsScore);

        var questions = AppConstants.QUESTIONS_SENTENCES;
        model.addAttribute("questions", questions);
        model.addAttribute("questionsScore", questionsScore);
        model.addAttribute("message", "Merci pour votre participation !");
        return "survey";
    }
}
