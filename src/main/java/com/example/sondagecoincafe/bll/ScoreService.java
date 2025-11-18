package com.example.sondagecoincafe.bll;

import com.example.sondagecoincafe.bo.Question;
import com.example.sondagecoincafe.bo.Score;

import java.util.List;

public interface ScoreService {

    List <Score > findAllScores ();

    int getTotalScore( List <Score> scores);

    int getTotalCountVote( List <Score> scores);

    float getWeightedGlobalRating(List <Score> scores);

    void incrementAndSaveTotalForScore(Question question, int responseScore);
}
