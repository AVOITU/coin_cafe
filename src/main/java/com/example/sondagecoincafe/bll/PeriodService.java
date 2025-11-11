package com.example.sondagecoincafe.bll;

import com.example.sondagecoincafe.bo.Period;
import com.example.sondagecoincafe.bo.Question;

import java.util.List;

public interface PeriodService {

    List< String > getListOfMonths(List<Period> periods);

    List< Float > getAverageScorePerMonth(List<Period> periods, int totalVoteCount);

    int calculateTotalVotes(List<Question> results);
}
