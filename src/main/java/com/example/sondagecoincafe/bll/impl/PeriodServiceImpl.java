package com.example.sondagecoincafe.bll.impl;

import com.example.sondagecoincafe.bll.PeriodService;
import com.example.sondagecoincafe.bo.Period;
import com.example.sondagecoincafe.dal.PeriodDao;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PeriodServiceImpl implements PeriodService {

    private final PeriodDao periodDao;

    public PeriodServiceImpl(PeriodDao periodDao) { this.periodDao = periodDao; }

    @Override
    public List <Period> findAllPeriodes(){
        return periodDao.findAll();
    }

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
    public List<Double> getAverageScorePerMonth(List<Period> periods, int totalVoteCount){

        List < Double > listOfAverageScore = new ArrayList<>();

        if (periods == null) return listOfAverageScore;

        int numberOfPeriods = 0;
        for (Period period : periods) {
            int periodTotalScore = period.getPeriodTotalScore();
            double averageTotalScore = (double) periodTotalScore / totalVoteCount;
            listOfAverageScore.add(averageTotalScore);
            numberOfPeriods += 1;
            if (numberOfPeriods == 5){
                return listOfAverageScore;
            }
        }

        return listOfAverageScore;
    }

    @Override
    public int calculateTotalVotes(List <Period> periods) {
        int totalVotes = 0;
        for (Period period : periods) {
            totalVotes += period.getPeriodTotalVotes();
        }
        return totalVotes;
    }
}