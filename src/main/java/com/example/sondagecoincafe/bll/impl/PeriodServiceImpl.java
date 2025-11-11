package com.example.sondagecoincafe.bll.impl;

import com.example.sondagecoincafe.bll.PeriodService;
import com.example.sondagecoincafe.bo.Period;
import com.example.sondagecoincafe.bo.Question;
import com.example.sondagecoincafe.dal.PeriodDao;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PeriodServiceImpl implements PeriodService {

    private final PeriodDao periodDao;

    public PeriodServiceImpl(PeriodDao periodDao) { this.periodDao = periodDao; }

    @Override
    public List< String > getListOfMonths(List<Period> periods){

        List < String > listOfMonths = new ArrayList<>();

        if (periods == null) return listOfMonths;

        int numberOfPeriods = 0;
        for (Period period : periods) {
            String periodMonth = period.getTimestampPeriod().getMonth().toString();
            listOfMonths.add(periodMonth);
            numberOfPeriods += 1;
            if (numberOfPeriods == 5){
                return listOfMonths;
            }
        }

        return listOfMonths;
    }

    @Override
    public List< Float > getAverageScorePerMonth(List<Period> periods, int totalVoteCount){

        List < Float > listOfAverageScore = new ArrayList<>();

        if (periods == null) return listOfAverageScore;

        int numberOfPeriods = 0;
        for (Period period : periods) {
            int periodTotalVotes = period.getPeriodTotalVotes();
            float averageTotalScore = (float) periodTotalVotes / totalVoteCount;
            listOfAverageScore.add(averageTotalScore);
            numberOfPeriods += 1;
            if (numberOfPeriods == 5){
                return listOfAverageScore;
            }
        }

        return listOfAverageScore;
    }

    @Override
    public int calculateTotalVotes(List<Question> results) {
        int totalVotes = 0;
        for (Question question : results) {
            for (Period period : question.getPeriods()) {
                totalVotes += period.getPeriodTotalVotes();
            }
        }
        return totalVotes;
    }
}
