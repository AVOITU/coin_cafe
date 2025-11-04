package com.example.sondagecoincafe.dal;

import com.example.sondagecoincafe.bo.Period;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeriodDao extends JpaRepository<Period, Long> {
}

