package com.example.sondagecoincafe.dal;

import com.example.sondagecoincafe.dto.ResultsDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionDao extends JpaRepository<ResultsDto.Question, Long> {
}
