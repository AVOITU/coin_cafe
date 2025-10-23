package com.example.sondagecoincafe.controller;

import com.example.sondagecoincafe.bll.UserService;
import com.example.sondagecoincafe.bo.User;
import com.example.sondagecoincafe.exceptions.BusinessCode;
import com.example.sondagecoincafe.exceptions.BusinessException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("users")
    public  String displayUserProfile(
            @RequestParam(name="username", required=true) String username,
            Model model
    ) {

        Optional<User> maybeUser = userService.getByUsername(username);
        maybeUser.ifPresent(user -> model.addAttribute("user", user));
        return "users/profile.html";
    }

    @GetMapping("users/register")
    public  String displayForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "security/register.html";
    }

    @PostMapping("users/register")
    public String registerUser(
            @Valid @ModelAttribute("user") User user,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "security/register.html";
        } else {
            try {
                userService.createUser(user);
            } catch (BusinessException e) {
                e.getKeys().forEach(key -> {
                    if (key.equals(BusinessCode.VALIDATION_USER_PASSWORD_CONFIRMATION)) {
                        FieldError fieldError = new FieldError("user", "passwordConfirmation", key);
                        bindingResult.addError(fieldError);
                    }
                });
                return "security/register.html";
            }
            return "redirect:/encheres";
        }

    }
}
