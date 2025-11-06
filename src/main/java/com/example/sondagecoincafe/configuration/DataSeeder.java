package com.example.sondagecoincafe.configuration;

import com.example.sondagecoincafe.bo.Question;
import com.example.sondagecoincafe.bo.Score;
import com.example.sondagecoincafe.dal.QuestionDao;
import com.example.sondagecoincafe.dal.ScoreDao;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@Profile("dev") // ⚠️ s’exécutera seulement si le profil actif est "dev"
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final QuestionDao questionDao;
    private final ScoreDao scoreDao;

    @Override
    public void run(String... args) {
        if (questionDao.count() == 0) {
            // --- Création des scores
            Score s1 = new Score();
            s1.setScore(5);
            s1.setScoreVoteCount(0);

            Score s2 = new Score();
            s2.setScore(4);
            s2.setScoreVoteCount(0);

            scoreDao.saveAll(Set.of(s1, s2));

            // --- Création d'une question
            Question q = new Question();
            q.setQuestionText("Hygiène");
            q.setQuestionTotalVotes(0);
            q.setAllVotesCount(0);
            q.getScores().addAll(Set.of(s1, s2));

            questionDao.save(q);
            System.out.println("✅ Données de test insérées !");
        }
    }
}

