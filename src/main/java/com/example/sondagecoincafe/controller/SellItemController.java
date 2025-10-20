package com.example.sondagecoincafe.controller;

import com.example.sondagecoincafe.bll.CategoryService;
import com.example.sondagecoincafe.bll.ItemService;
import com.example.sondagecoincafe.bll.UserService;
import com.example.sondagecoincafe.bo.Category;
import com.example.sondagecoincafe.bo.Item;
import com.example.sondagecoincafe.bo.User;
import jakarta.validation.Valid;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class SellItemController {

    private final DataSourceTransactionManagerAutoConfiguration dataSourceTransactionManagerAutoConfiguration;
    private ItemService itemService;
    private CategoryService categoryService;
    private UserService userService;

    public SellItemController(ItemService itemService, CategoryService categoryService, UserService userService, DataSourceTransactionManagerAutoConfiguration dataSourceTransactionManagerAutoConfiguration) {
        this.itemService = itemService;
        this.categoryService = categoryService;
        this.userService = userService;
        this.dataSourceTransactionManagerAutoConfiguration = dataSourceTransactionManagerAutoConfiguration;
    }

    @GetMapping("/sell")
    public String displayItem(Item item, Model model) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "sell.html";
    }

    @PostMapping("/sell")
    public String createItem(
            @Valid @ModelAttribute("item") Item item,
            BindingResult errors,
            Principal principal
    ) {
        Optional<User> loggedUser = userService.getByUsername(principal.getName());
        loggedUser.ifPresent(user -> item.setOwner(user));
        if (errors.hasErrors()) {
            return "sell.html";
        }
        itemService.createItemWithCategory(item);
        return "redirect:/encheres";
    }


 }
