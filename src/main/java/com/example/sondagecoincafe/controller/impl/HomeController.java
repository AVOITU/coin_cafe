package com.example.sondagecoincafe.controller.impl;

import com.example.sondagecoincafe.dto.SurveyDto;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@NoArgsConstructor
public class HomeController {


    @GetMapping("")
    public String localhostPort(){
        return "redirect:survey";
    }

    @GetMapping("/survey")
    public String displayItems(Model model) {

        return "survey";
    }


    @PostMapping("/survey")
    public String handleSurveySubmit(@ModelAttribute SurveyDto surveyDto, Model model) {
        // 🔹 Traitement des réponses ici (ex: enregistrement BDD)
        model.addAttribute("message", "Merci pour votre participation !");
        return "survey"; // page de confirmation
    }
}
