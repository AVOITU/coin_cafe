package com.example.sondagecoincafe.bll;

import com.example.sondagecoincafe.bo.Result;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ResultService {

    List<Result> getDtoResults();
    float calculateAverageRating(List<Result> results);
    List<String> getTotalVoteCounts(List<Result> results);
    public List<Float> getQuestionGlobalNotations(List<Result> results);
}