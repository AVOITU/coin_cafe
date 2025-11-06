package com.example.sondagecoincafe.configuration;

import com.example.sondagecoincafe.bo.Period;
import com.example.sondagecoincafe.bo.Question;
import com.example.sondagecoincafe.bo.Score;
import com.example.sondagecoincafe.dal.PeriodDao;
import com.example.sondagecoincafe.dal.QuestionDao;
import com.example.sondagecoincafe.dal.ScoreDao;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Component
@Profile("dev") // ne s’exécutera qu’en profil dev
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final QuestionDao questionDao;
    private final PeriodDao periodDao;
    private final ScoreDao scoreDao;

    private final Faker faker = new Faker(new Locale("fr"));
    private final Random random = new Random();

    @Override
    public void run(String... args) {

        System.out.println("🚀 Initialisation des données fictives...");

        // Nettoyage optionnel
        questionDao.deleteAll();
        periodDao.deleteAll();
        scoreDao.deleteAll();

        // 1️⃣ Génération des scores
        List<Score> scores = scoreDao.saveAll(
                Set.of(
                        newScore(0), newScore(1), newScore(2),
                        newScore(3), newScore(4), newScore(5)
                )
        );

        // 2️⃣ Génération des périodes
        Set<Period> periods = new HashSet<>();
        for (int i = 0; i < 8; i++) {
            Period p = new Period();
            p.setTimestampPeriod(LocalDateTime.now(ZoneId.of("Europe/Paris")));
            periodDao.save(p);
            p.setPeriodTotalVotes(BigDecimal.valueOf(random.nextDouble(5)));
            periodDao.save(p);
            periods.add(p);
        }

        // 3️⃣ Génération des questions
        for (int i = 0; i < 12; i++) {
            Question q = new Question();
            q.setQuestionText(faker.lorem().sentence(3));
            q.setQuestionTotalVotes(random.nextInt(100));
            q.setAllVotesCount(random.nextInt(200));
            q.setChatgptComments(faker.lorem().sentence(6));

            // relations aléatoires
            q.getScores().addAll(scores.subList(0, random.nextInt(scores.size())));
            q.getPeriods().addAll(periods);

            questionDao.save(q);
        }

        System.out.println("✅ Données fictives générées avec succès !");
    }

    private Score newScore(int value) {
        Score s = new Score();
        s.setScore(value);
        s.setScoreVoteCount(random.nextInt(50));
        return s;
    }
}
