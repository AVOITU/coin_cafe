package com.example.sondagecoincafe.bll.impl;

import com.example.sondagecoincafe.bll.ScoreService;
import com.example.sondagecoincafe.bo.Score;
import com.example.sondagecoincafe.configuration.AppConstants;
import com.example.sondagecoincafe.dal.ScoreDao;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Getter @Setter
public class ScoreServiceImpl implements ScoreService {

    private ScoreDao scoreDao;

    public ScoreServiceImpl(ScoreDao scoreDao) {
        this.scoreDao = scoreDao;
    }

    @Override
    public List<Score> findAllScores() {
        return scoreDao.findAll();
    }

    @Override
    public int getTotalScore( List <Score> scores){
        if (scores == null) return 0;
        int totalScore = 0;
        for (Score score : scores){
            if ( score.getScore() > 0){
                totalScore += score.getScore() * score.getScoreVoteCount();
            }
        }
        return totalScore;
    }

    @Override
    public int getTotalCountVote( List <Score> scores){
        if (scores == null) return 0;
        int totalCountVote = 0;
        for (Score score : scores){
            if ( score.getScore() > 0){
                totalCountVote += score.getScoreVoteCount();
            }
        }
        return totalCountVote;
    }

    @Override
    public float getWeightedGlobalRating(List <Score> scores){
        if (scores == null) return 0f;
        int totalScore = getTotalScore(scores);
        int totalCountVote = getTotalCountVote(scores);
        return (totalCountVote == 0) ? 0f : (float) totalScore / totalCountVote;
    }
}
