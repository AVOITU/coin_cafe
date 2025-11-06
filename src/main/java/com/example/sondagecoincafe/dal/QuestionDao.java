package com.example.sondagecoincafe.dal;

import com.example.sondagecoincafe.bo.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionDao extends JpaRepository<Question, Long> {
}
