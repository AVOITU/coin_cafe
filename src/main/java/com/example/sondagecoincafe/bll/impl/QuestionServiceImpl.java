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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public float calculateAverageRating(List<Question> results) {
        float averageRating = 0;
        int totalSum = 0;
        int totalVotes =0;

        if (results == null) return 0f; // évite un NullPointerException si la liste passée est null.
        for (Question question : results) {

            for (Score score : question.getScores()) {
                int votes = score.getScoreVoteCount();
                int value = score.getScore();
                totalSum += value * votes;
            }

            for (Period period : question.getPeriods()){
                int numberOfVotes = period.getPeriodTotalVotes();
                totalVotes += numberOfVotes;
            }
        }

        averageRating = (float) totalSum / totalVotes;
        return totalVotes == 0 ? 0f : averageRating;
    }

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

    public List < String > getListOfMonths(List<Question> results){

        List < String > listOfMonths = List.of();

        if (results == null) return listOfMonths;

        for (Question question : results){
            int numberOfPeriods = 0;
            for (Period period : question.getPeriods()) {
                String periodMonth = period.getTimestampPeriod().getMonth().toString();
                listOfMonths.add(periodMonth);
                numberOfPeriods += 1;
                if (numberOfPeriods == 4){
                    return listOfMonths;
                }
            }
            }

        return listOfMonths;
    }

//    public List<String> getTotalVoteCounts(List<Question> results){
//        List<String> totalVoteCounts = results.stream()
//                .map(Question::getQuestionName)
//                .toList();
//        return totalVoteCounts;
//    }

//    public List<Float> getQuestionGlobalNotations(List<Question> results){
//        List<Float> questionGlobalNotations = results.stream()
//                .map(Question::getQuestionGlobalNotation)
//                .toList();
//        return questionGlobalNotations;
//    }
}
