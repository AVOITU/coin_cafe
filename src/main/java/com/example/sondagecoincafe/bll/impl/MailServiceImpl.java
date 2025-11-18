package com.example.sondagecoincafe.bll.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Service
public class MailServiceImpl{

    private final JavaMailSender mailSender;

    @Value("${app.base-url}")
    private String appBaseUrl;

    public MailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendResetPasswordEmail(String to, String token) {
        String resetUrl = appBaseUrl + "/reset-password?token=" + token;

        String subject = "Réinitialisation de votre mot de passe";
        String text = "Bonjour,\n\n" +
                "Vous avez demandé la réinitialisation de votre mot de passe.\n" +
                "Veuillez cliquer sur le lien suivant (valable 1 heure) :\n" +
                resetUrl + "\n\n" +
                "Si vous n'êtes pas à l'origine de cette demande, ignorez ce message.\n";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        mailSender.send(message);
    }
}
