package com.example.sondagecoincafe.bll;

import com.example.sondagecoincafe.bo.Period;

import java.sql.Timestamp;
import java.util.List;

public interface PeriodService {

    List <Period> findAllPeriodes();

    List<Double> getAverageScorePerMonth(List<Period> periods);

    List< String > getListOfMonths(List<Period> periods);

    Period incrementTotalsPeriode(int newTotalScore);

    Period getOrCreateCurrentPeriodByTimestamp(Timestamp actualTimestamp);
}
