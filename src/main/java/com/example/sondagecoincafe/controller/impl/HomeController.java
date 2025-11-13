package com.example.sondagecoincafe.controller.impl;

import com.example.sondagecoincafe.bll.*;
import com.example.sondagecoincafe.configuration.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private SurveyService surveyService;

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
    public String handleSurveySubmit(@ModelAttribute Map <String, Integer> questionsScore, Model model) {

        surveyService.processSurvey(questionsScore);

        model.addAttribute("message", "Merci pour votre participation !");
        return "survey";
    }
}
