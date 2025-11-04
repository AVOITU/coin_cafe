package com.example.sondagecoincafe.dal;

import com.example.sondagecoincafe.bo.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionDao extends JpaRepository<Question, Long> {
}
