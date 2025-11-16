package com.example.sondagecoincafe.dal;

import com.example.sondagecoincafe.bo.Period;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface PeriodDao extends JpaRepository<Period, Long> {
    Optional<Period> findCurrentPeriodByTimestampPeriod(LocalDateTime timestampPeriod);

    @Modifying
    @Transactional
    @Query("DELETE FROM Period p WHERE p.timestampPeriod < :limitDate")
    void deleteOldPeriods(@Param("limitDate") LocalDateTime limitDate);
}

