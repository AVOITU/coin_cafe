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
            "Hygiène du coin café ?", "L'accueil au coin café ?", "L’ambiance au coin café ?",
            "Accessibilité du coin café ?",
            "Indications pour accéder au coin café ?", "Qualité du service ?",
            "Qualité des produits vendus ?", "Rapport qualité/prix des produits vendus ?",
            "Diversité des produits ?", "Connexion WI‑FI ?"
    };
    public static final int MAX_NUMBER_OF_QUESTIONS = QUESTIONS_SENTENCES.length;
}