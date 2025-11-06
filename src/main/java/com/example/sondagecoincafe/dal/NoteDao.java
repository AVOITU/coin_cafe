package com.example.sondagecoincafe.dal;

import com.example.sondagecoincafe.bo.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteDao extends JpaRepository<Score,Long> {
}
