package com.example.sondagecoincafe.controller.impl;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {

    private ItemService itemService;
    private CategoryService categoryService;

    public HomeController(ItemService itemService, CategoryService categoryService) {
        this.itemService = itemService;
        this.categoryService=categoryService;
    }

    @GetMapping("")
    public String localhostPort(){
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

    @PostMapping("/encheres")
    public String filterCategory(@RequestParam("category") Integer noCategory, Model model
    ) {
        List<Item> itemsFilteredByCategory=itemService.getAllItemsByCategoryId(noCategory);
        model.addAttribute("items", itemsFilteredByCategory);

        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "encheres";
    }
}
