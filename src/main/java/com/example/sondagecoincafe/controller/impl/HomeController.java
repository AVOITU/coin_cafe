package com.example.sondagecoincafe.controller.impl;

import com.example.sondagecoincafe.configuration.AppConstants;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@NoArgsConstructor
public class HomeController {


    @GetMapping("")
    public String localhostPort(Model model){

        var questions = AppConstants.QUESTIONS_SENTENCES;
        model.addAttribute(questions);
        return "redirect:survey";
    }

    @GetMapping("/survey")
    public String displayItems(Model model) {

//        List<Item> items = itemService.findItemsInProgress();
//        model.addAttribute("items", items);
//
//        List<Category> categories = categoryService.getAllCategories();
//        model.addAttribute("categories", categories);

        return "survey";
    }
}
