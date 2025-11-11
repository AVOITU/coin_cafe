package com.example.sondagecoincafe.bll;

import com.example.sondagecoincafe.bo.Period;

import java.util.List;

public interface PeriodService {

    List <Period> findAllPeriodes();

    int calculateTotalVotes(List <Period> periods);

    List<Double> getAverageScorePerMonth(List<Period> periods, int totalVoteCount);

    List< String > getListOfMonths(List<Period> periods);
}
