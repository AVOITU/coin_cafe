package com.example.sondagecoincafe.controller.impl;

import com.example.sondagecoincafe.bll.PeriodService;
import com.example.sondagecoincafe.bll.QuestionService;
import com.example.sondagecoincafe.bll.ResultDtoService;
import com.example.sondagecoincafe.bll.ScoreService;
import com.example.sondagecoincafe.bo.Question;
import com.example.sondagecoincafe.configuration.AppConstants;
import com.example.sondagecoincafe.dto.SurveyDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

    private final QuestionService questionService;
    private final PeriodService periodService;
    private final ScoreService scoreService;

    public HomeController(QuestionService questionService, PeriodService periodService, ScoreService scoreService, ResultDtoService resultDtoService) {
        this.questionService = questionService;
        this.periodService = periodService;
        this.scoreService = scoreService;
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
    public String handleSurveySubmit(@ModelAttribute Map <String, Integer> questionsScore, Model model) {

        List <Question> questions = questionService.findAllQuestion();
        questionService.getListVotesWithScore(questions);

        model.addAttribute("message", "Merci pour votre participation !");
        return "survey";
    }
}
