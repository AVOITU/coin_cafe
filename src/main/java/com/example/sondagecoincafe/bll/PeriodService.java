package com.example.sondagecoincafe.bll;

import com.example.sondagecoincafe.bo.Period;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public interface PeriodService {

    List <Period> findAllPeriodes();

    List<Double> getAverageScorePerMonth(List<Period> periods);

    List< String > getListOfMonths(List<Period> periods);

    void incrementAndSaveTotalsPeriod(int score, Period currentPeriod);

    Period getOrCreateCurrentPeriodByTimestamp();

    void deletePeriodsOlderThan12Months();
}
