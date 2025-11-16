package com.example.sondagecoincafe.bll.impl;

import com.example.sondagecoincafe.bll.PeriodService;
import com.example.sondagecoincafe.bo.Period;
import com.example.sondagecoincafe.dal.PeriodDao;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

@Service
public class PeriodServiceImpl implements PeriodService {

    private final PeriodDao periodDao;

    public PeriodServiceImpl(PeriodDao periodDao) { this.periodDao = periodDao; }

    @Override
    public List <Period> findAllPeriodes(){
        List<Period> periods = periodDao.findAll();
        Collections.reverse(periods);
        return periods;
    }

    @Override
    public List< String > getListOfMonths(List<Period> periods){

        List < String > listOfMonths = new ArrayList<>();

        if (periods == null) return listOfMonths;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM", Locale.FRENCH);

        int numberOfPeriods = 0;
        for (Period period : periods) {
            String periodMonth = period.getTimestampPeriod().format(formatter).toUpperCase(Locale.ROOT);
            listOfMonths.add(periodMonth);
            numberOfPeriods += 1;
            if (numberOfPeriods == 5){
                Collections.reverse(listOfMonths);
                return listOfMonths;
            }
        }

        Collections.reverse(listOfMonths);
        return listOfMonths;
    }

    @Override
    public List<Double> getAverageScorePerMonth(List<Period> periods){

        List < Double > listOfAverageScore = new ArrayList<>();

        if (periods == null) return listOfAverageScore;

        int numberOfPeriods = 0;
        for (Period period : periods) {
            double averageTotalScore = (double) period.getPeriodTotalScore() / period.getPeriodTotalVotes();
            averageTotalScore = Math.round(averageTotalScore * 10.0) / 10.0;
            listOfAverageScore.add(averageTotalScore);
            numberOfPeriods += 1;
            if (numberOfPeriods == 5){
                return listOfAverageScore;
            }
        }

        return listOfAverageScore;
    }

    @Override
    public Period incrementTotalsPeriode(int score){
        LocalDateTime firstDayOfMonth = LocalDateTime.now()
                .withDayOfMonth(1)
                .withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);

        // 3) période courante (mois en cours par ex.)
        Period currentPeriod = getOrCreateCurrentPeriodByTimestamp(Timestamp.valueOf(firstDayOfMonth));

        int currentPeriodTotalVotes = currentPeriod.getPeriodTotalVotes() +1;
        int currentPeriodTotalScore = currentPeriod.getPeriodTotalScore() + score;

        currentPeriod.setPeriodTotalVotes(currentPeriodTotalVotes);
        currentPeriod.setPeriodTotalScore(currentPeriodTotalScore);

        return currentPeriod;
    }

    @Override
    public Period getOrCreateCurrentPeriodByTimestamp(Timestamp actualTimestamp) {

        return periodDao.findCurrentPeriodByTimestampPeriod(actualTimestamp.toLocalDateTime())
                .orElseGet(() -> {
                    Period p = new Period();
                    p.setTimestampPeriod(actualTimestamp.toLocalDateTime());
                    p.setPeriodTotalVotes(0);
                    p.setPeriodTotalScore(0);
                    return periodDao.save(p);
                });
    }
}