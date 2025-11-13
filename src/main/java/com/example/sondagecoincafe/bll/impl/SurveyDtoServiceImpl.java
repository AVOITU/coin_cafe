package com.example.sondagecoincafe.bll.impl;

import com.example.sondagecoincafe.bll.SurveyDtoService;
import com.example.sondagecoincafe.bo.Period;
import com.example.sondagecoincafe.bo.Question;
import com.example.sondagecoincafe.bo.Score;
import com.example.sondagecoincafe.dal.PeriodDao;
import com.example.sondagecoincafe.dal.QuestionDao;
import com.example.sondagecoincafe.dal.ScoreDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class SurveyDtoServiceImpl implements SurveyDtoService {


    private final QuestionDao questionDao;
    private final ScoreDao scoreDao;
    private final PeriodDao periodDao;

    public SurveyDtoServiceImpl(QuestionDao questionDao, ScoreDao scoreDao, PeriodDao periodDao) {
        this.questionDao = questionDao;
        this.scoreDao = scoreDao;
        this.periodDao = periodDao;
    }

    @Transactional
    public void processSurvey(Map<String, Integer> questionsScore) {

        List<Question> questions = questionDao.findAll();

        int newTotalScore = 0;

        for (Question question : questions) {
            String searchedQuestion = question.getQuestionText();
            int scoreQuestionSearched = questionsScore.get(searchedQuestion);
// TO DO : associer question à catégorie

            newTotalScore = question.getQuestionTotalScore() + scoreQuestionSearched;
            question.setQuestionTotalScore(newTotalScore);

            if (questionsScore.containsKey(searchedQuestion)) {
                int newQuestionTotalVotes = question.getQuestionTotalVotes() + 1;
                question.setQuestionTotalVotes(newQuestionTotalVotes);
            }

            questionDao.save(question);

            Score score = scoreDao.findByScore(scoreQuestionSearched);
            int totalScoreVoteCount = score.getScoreVoteCount() +1;
            score.setScoreVoteCount(totalScoreVoteCount);
            scoreDao.save(score);
        }

        LocalDateTime firstDayOfMonth = LocalDateTime.now()
                .withDayOfMonth(1)
                .withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);

        // 3) période courante (mois en cours par ex.)
        Period currentPeriod = getOrCreateCurrentPeriodByTimestamp(Timestamp.valueOf(firstDayOfMonth));

        int currentPeriodTotalVotes = currentPeriod.getPeriodTotalVotes() +1;
        int currentPeriodTotalScore = currentPeriod.getPeriodTotalScore() + newTotalScore;

        currentPeriod.setPeriodTotalVotes(currentPeriodTotalVotes);
        currentPeriod.setPeriodTotalScore(currentPeriodTotalScore);

        periodDao.save(currentPeriod);

    }

    private void incrementTotalsForQuestion(Question question, int answer, Period period) {

        // score correspondant (1..5)
        Score score = scoreDao.findByScore(answer);

        // incrémenter le nombre de votes pour cette note
        score.setScoreVoteCount(score.getScoreVoteCount() + 1);

        // incrémenter le total de votes de la question
        question.setQuestionTotalVotes(question.getQuestionTotalVotes() + 1);
        question.setQuestionTotalVotes(question.getQuestionTotalVotes() + 1);

        // incrémenter la période
        period.setPeriodTotalVotes(period.getPeriodTotalVotes() + 1);

        // sauvegarde (cascade si configuré, sinon save les 3)
        scoreDao.save(score);
        questionDao.save(question);
        periodDao.save(period);
    }

    private Period getOrCreateCurrentPeriodByTimestamp(Timestamp actualTimestamp) {

        return periodDao.findCurrentPeriodByTimestampPeriod(actualTimestamp)
                .orElseGet(() -> {
                    Period p = new Period();
                    p.setTimestampPeriod(LocalDateTime.now());
                    p.setPeriodTotalVotes(0);
                    p.setPeriodTotalScore(0);
                    return periodDao.save(p);
                });
    }
}

