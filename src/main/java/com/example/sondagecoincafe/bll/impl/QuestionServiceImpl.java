package com.example.sondagecoincafe.bll.impl;

import com.example.sondagecoincafe.bll.QuestionService;
import com.example.sondagecoincafe.bo.Question;
import com.example.sondagecoincafe.bo.Score;
import com.example.sondagecoincafe.dal.QuestionDao;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Getter @Setter
public class QuestionServiceImpl implements QuestionService {

    private QuestionDao questionDao;

    public QuestionServiceImpl(QuestionDao resultDao){
        this.questionDao = resultDao;
    }

    public List<Question> getDtoResults() {
        return questionDao.findAllWithRelations();
    }

//    boucle sur les scores des scorescar potentiellement la table la moins remplie
public float calculateAverageRating(List<Question> results) {
    int totalVotes = 0;
    int weightedSum = 0;

    if (results == null) return 0f;
    for (Question question : results) {
        if (question.getScores() == null) continue;
        for (Score score : question.getScores()) {
            int votes = score.getScoreVoteCount();
            int value = score.getScore();
            if (votes <= 0) continue;      // ignore zéros
            weightedSum += value * votes;
            totalVotes += votes;
        }
    }
    return totalVotes == 0 ? 0f : (float) weightedSum / (float) totalVotes;
}

//    public List<String> getTotalVoteCounts(List<Question> results){
//        List<String> totalVoteCounts = results.stream()
//                .map(Question::getQuestionName)
//                .toList();
//        return totalVoteCounts;
//    }
//
//    public List<Float> getQuestionGlobalNotations(List<Question> results){
//        List<Float> questionGlobalNotations = results.stream()
//                .map(Question::getQuestionGlobalNotation)
//                .toList();
//        return questionGlobalNotations;
//    }
}
