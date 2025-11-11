package com.example.sondagecoincafe.bll.impl;

import com.example.sondagecoincafe.bll.QuestionService;
import com.example.sondagecoincafe.bo.Period;
import com.example.sondagecoincafe.bo.Question;
import com.example.sondagecoincafe.bo.Score;
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

//    public List<Float> getQuestionGlobalNotations(List<Question> results){
//        List<Float> questionGlobalNotations = results.stream()
//                .map(Question::getQuestionGlobalNotation)
//                .toList();
//        return questionGlobalNotations;
//    }
}
