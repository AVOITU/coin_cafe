package com.example.sondagecoincafe.bll.impl;

import com.example.sondagecoincafe.bll.QuestionService;
import com.example.sondagecoincafe.bo.Period;
import com.example.sondagecoincafe.bo.Question;
import com.example.sondagecoincafe.bo.Score;
import com.example.sondagecoincafe.configuration.AppConstants;
import com.example.sondagecoincafe.dal.QuestionDao;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Getter @Setter
public class QuestionServiceImpl implements QuestionService {

    private QuestionDao questionDao;

    public QuestionServiceImpl(QuestionDao questionDao){
        this.questionDao = questionDao;
    }

    @Override
    public List<Question> getDtoResults() {
        return questionDao.findAllWithRelations();
    }

    @Override
    public Map <Integer, Integer> getListVotesWithScore(List<Question> results) {

        Map <Integer, Integer> mapForPieCount = new HashMap<>(Map.of());

        if (results == null) return mapForPieCount;

        for (Question question : results){
            for (Score score : question.getScores()) {
                if (score.getScore() > 0){
                    int scoreValue = score.getScore();
                    int votes = score.getScoreVoteCount();
                    mapForPieCount.put(scoreValue, votes);
                }
            }
        }

        return mapForPieCount;
    }

    @Override
    public List <String> getTagsFromQuestionList(List <Question> questions){

        if (questions == null) return null;

        List <String> tagsFromList = new ArrayList<>();
        for (Question question : questions){
            String tag = question.getTag();
            tagsFromList.add(tag);
        }
        return tagsFromList;
    }

    @Override
    public List <Double> calculateAverageByTag(List <Question> questions){

        if (questions == null) return null;

        double averageQuestion = 0;
        List <Double> averagesByQuestion = new ArrayList<>();
        for (Question question : questions){
            averageQuestion = (double) question.getQuestionTotalScore() / question.getQuestionTotalVotes();
            averageQuestion = Math.round(averageQuestion * 10.0) / 10.0; // arrondi à 1 chiffre après la virgule
            averagesByQuestion.add(averageQuestion);
        }
        return averagesByQuestion;
    }
}
