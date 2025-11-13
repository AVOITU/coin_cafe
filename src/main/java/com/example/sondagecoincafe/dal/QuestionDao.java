package com.example.sondagecoincafe.dal;

import com.example.sondagecoincafe.bo.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDao extends JpaRepository<Question, Long> {

    @Query("select distinct q from Question q inner join fetch q.periods inner join fetch q.scores")
    List<Question> findAllWithRelations();

    List<Question> findAllByOrderByIdAsc();
}
