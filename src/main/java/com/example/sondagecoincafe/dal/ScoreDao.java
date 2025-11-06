package com.example.sondagecoincafe.dal;

import com.example.sondagecoincafe.bo.Score;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScoreDao extends JpaRepository <Score, Long> {
}
