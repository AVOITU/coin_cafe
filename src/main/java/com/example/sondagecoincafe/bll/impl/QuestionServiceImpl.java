package com.example.sondagecoincafe.bll.impl;

import com.example.sondagecoincafe.bll.QuestionService;
import com.example.sondagecoincafe.bo.Question;
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
        return questionDao.findAll();
    }

    public float calculateAverageRating(List<Question> results){
        float averageGlobalRating = 1;
        return averageGlobalRating;
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
