package com.example.sondagecoincafe.configuration;

import java.lang.reflect.Array;
import java.util.List;

public class AppConstants {
    public static final int MAX_SCORE = 5;
    public static final List<String> TAGS = List.of(
            "Hygiène", "Accueil", "Ambiance", "Accessibilité",
            "Signalétique", "Service", "Produits", "Qualité/Prix",
            "Diversité", "Wi-Fi"
    );
    public static final String[] QUESTIONS_SENTENCES = {
            "1) Hygiène du coin café ?", "2) L'accueil au coin café ?", "3) L’ambiance au coin café ?",
            "4) Accessibilité du coin café ?",
            "5) Indications pour accéder au coin café ?", "6) Qualité du service ?",
            "7) Qualité des produits vendus ?", "8) Rapport qualité/prix des produits vendus ?",
            "9) Diversité des produits ?", "10) Connexion WI‑FI ?"
    };
    public static final int MAX_NUMBER_OF_QUESTIONS = QUESTIONS_SENTENCES.length;
}