package com.example.sondagecoincafe.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public interface ResultController {
    @GetMapping("/results")
    String updateResults(Model model);
}
