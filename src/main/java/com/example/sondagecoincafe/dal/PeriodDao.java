package com.example.sondagecoincafe.dal;

import com.example.sondagecoincafe.bo.Period;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeriodDao extends JpaRepository<Period, Long> {
}

