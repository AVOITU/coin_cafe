package com.example.sondagecoincafe.controller.impl;

import com.example.sondagecoincafe.configuration.AppConstants;
import com.example.sondagecoincafe.dto.SurveyDto;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@NoArgsConstructor
public class HomeController {


    @GetMapping("")
    public String localhostPort(){
        return "redirect:/survey";
    }

    @GetMapping("/survey")
    public String displayItems(Model model) {
        var questions = AppConstants.QUESTIONS_SENTENCES;
        model.addAttribute("questions", questions);
        model.addAttribute("surveyDto", new SurveyDto());
        return "survey";
    }


    @PostMapping("/survey")
    public String handleSurveySubmit(@ModelAttribute SurveyDto surveyDto, Model model) {
        // 🔹 Traitement des réponses ici (ex: enregistrement BDD)
        model.addAttribute("message", "Merci pour votre participation !");
        return "survey";
    }
}
