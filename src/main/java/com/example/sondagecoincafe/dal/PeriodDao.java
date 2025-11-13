package com.example.sondagecoincafe.dal;

import com.example.sondagecoincafe.bo.Period;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface PeriodDao extends JpaRepository<Period, Long> {
    Optional<Period> findCurrentPeriodByTimestampPeriod(Timestamp timestampPeriod);
}

