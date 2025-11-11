package com.example.sondagecoincafe.bll;

import com.example.sondagecoincafe.bo.Period;
import com.example.sondagecoincafe.bo.Question;

import java.util.List;

public interface PeriodService {

    int calculateTotalVotes(List<Question> results);

    List< Float > getAverageScorePerMonth(List<Period> periods, int totalVoteCount);

    List< String > getListOfMonths(List<Period> periods);
}
