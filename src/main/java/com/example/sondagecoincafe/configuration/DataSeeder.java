package com.example.sondagecoincafe.configuration;

import com.example.sondagecoincafe.bll.UserService;
import com.example.sondagecoincafe.bo.Period;
import com.example.sondagecoincafe.bo.Question;
import com.example.sondagecoincafe.bo.Score;
import com.example.sondagecoincafe.bo.User;
import com.example.sondagecoincafe.dal.PeriodDao;
import com.example.sondagecoincafe.dal.QuestionDao;
import com.example.sondagecoincafe.dal.ScoreDao;
import com.example.sondagecoincafe.dal.UserDao;
import com.github.javafaker.Faker;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
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
    private final EntityManager entityManager;

    private final Faker faker = new Faker(new Locale("fr"));
    private final Random random = new Random();

    @Override
    public void run(String... args) {

        System.out.println("🚀 Initialisation des données fictives...");

        // Nettoyage optionnel
        questionDao.deleteAll();
        periodDao.deleteAll();
        scoreDao.deleteAll();

        // Génération scores
        List<Score> scores = List.of(
                        newScore(0), newScore(1), newScore(2),
                        newScore(3), newScore(4), newScore(5)
        );

        List<Period> periods = new ArrayList<>();
        ZoneId tz = ZoneId.of("Europe/Paris");
        LocalDateTime anchor = LocalDate.now(tz).withDayOfMonth(1).atStartOfDay();

        for (int i = 14; i >= 0; i--) {
            Period p = new Period();
            p.setTimestampPeriod(anchor.minusMonths(i));
            int randomVoters = random.nextInt(20);
            p.setPeriodTotalVotes(randomVoters);
            p.setPeriodTotalScore(random.nextInt(
                    ((randomVoters*AppConstants.MAX_SCORE) - randomVoters + 1) + randomVoters));
            periods.add(p);
        }

        scoreDao.saveAll(scores);
        periodDao.saveAll(periods);

        // Génération questions
        for (int i = 0; i < AppConstants.MAX_NUMBER_OF_QUESTIONS; i++) {
            Question q = new Question();
            q.setQuestionText(AppConstants.QUESTIONS_SENTENCES[i]);
            q.setTag(AppConstants.TAGS.get(i));
            int randomVoters = random.nextInt(100);
            q.setQuestionTotalVotes(randomVoters);
            q.setQuestionTotalScore(random.nextInt(
                    ((randomVoters*AppConstants.MAX_SCORE) - randomVoters + 1) + randomVoters));

            q.setChatgptComments(faker.lorem().sentence(6));

            int n = 1 + random.nextInt(scores.size()); // [1..size]
            q.getScores().addAll(scores.subList(0, n));
            q.getPeriods().addAll(periods);

            entityManager.clear();

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
