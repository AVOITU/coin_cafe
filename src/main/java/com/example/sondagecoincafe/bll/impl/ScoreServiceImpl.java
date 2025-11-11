package com.example.sondagecoincafe.bll.impl;

import com.example.sondagecoincafe.bll.ScoreService;
import com.example.sondagecoincafe.bo.Score;
import com.example.sondagecoincafe.dal.ScoreDao;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Getter @Setter
@NoArgsConstructor
public class ScoreServiceImpl implements ScoreService {

    private ScoreDao scoreDao;

    public ScoreServiceImpl(ScoreDao scoreDao) {
        this.scoreDao = scoreDao;
    }

    @Override
    public List<Score> findAllScores() {
        return scoreDao.findAll();
    }
}
