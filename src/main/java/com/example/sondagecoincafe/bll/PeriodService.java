package com.example.sondagecoincafe.bll;

import com.example.sondagecoincafe.bo.Period;

import java.util.List;

public interface PeriodService {

    int getTotalVoteCount();

    List< String > getListOfMonths(List<Period> periods);

    List< Float > getAverageScorePerMonth(List<Period> periods, int totalVoteCount);


}
