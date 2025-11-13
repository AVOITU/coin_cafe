package com.example.sondagecoincafe.dal;

import com.example.sondagecoincafe.bo.Question;
import com.example.sondagecoincafe.bo.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreDao extends JpaRepository <Score, Long> {

//    Score findByQuestionsAndScore(Question question, int answer);
}
