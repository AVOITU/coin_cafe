package com.example.sondagecoincafe.controller.impl;

import com.example.sondagecoincafe.bll.PasswordResetTokenService;
import com.example.sondagecoincafe.bll.UserService;
import com.example.sondagecoincafe.bll.impl.MailServiceImpl;
import com.example.sondagecoincafe.bo.User;
import com.example.sondagecoincafe.configuration.security.PasswordResetToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Controller
public class LoginController {

    private final UserService userService;
    private final PasswordResetTokenService passwordResetTokenService;
    private final MailServiceImpl mailService;
    private final PasswordEncoder passwordEncoder;

    public LoginController(UserService userService,
                           PasswordResetTokenService passwordResetTokenService,
                           MailServiceImpl mailService,
                           PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordResetTokenService = passwordResetTokenService;
        this.mailService = mailService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String login(
            @RequestParam(value = "error", required = false) String error,
            Model model
    ) {
        if (error != null) {
            model.addAttribute("error", error);
        }
        return "security/login";
    }

    @PostMapping("/forgot-password")
    public String processForgot(@RequestParam String email) {
        User user = userService.getByEmail(email);

        if (user != null) {
            String token = UUID.randomUUID().toString();

            PasswordResetToken prt = new PasswordResetToken();
            prt.setToken(token);
            prt.setUser(user);
            prt.setExpiresAt(LocalDateTime.now().plusHours(1));
            passwordResetTokenService.savePasswordResetTokenRepo(prt);

            mailService.sendResetPasswordEmail(user.getEmail(), token);
        }

        // même réponse, que l'email existe ou non
        return "security/forgot-password-sent";
    }

    @PostMapping("/reset-password")
    public String handleReset(@RequestParam String token,
                              @RequestParam String password,
                              @RequestParam String confirmPassword) {

        PasswordResetToken prt = passwordResetTokenService.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Token invalide"));

        if (prt.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Token expiré");
        }
        if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException("Les mots de passe ne correspondent pas");
        }

        User user = prt.getUser();
        user.setPassword(passwordEncoder.encode(password));
        userService.updateUser(user);

        passwordResetTokenService.delete(prt); // token à usage unique
        return "security/reset-password-success";
    }

    @GetMapping("/reset-password")
    public String showResetForm(@RequestParam String token, Model model) {
        model.addAttribute("token", token);
        return "security/reset-password";
    }

    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "security/forgot-password";
    }

    @GetMapping("/reset-password-success")
    public String showResetPasswordSuccessForm() {
        return "security/reset-password-success";
    }
}