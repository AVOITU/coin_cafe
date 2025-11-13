package com.example.sondagecoincafe.bll.impl;

import com.example.sondagecoincafe.bll.SurveyDtoService;
import com.example.sondagecoincafe.bo.Period;
import com.example.sondagecoincafe.bo.Question;
import com.example.sondagecoincafe.bo.Score;
import com.example.sondagecoincafe.dal.PeriodDao;
import com.example.sondagecoincafe.dal.QuestionDao;
import com.example.sondagecoincafe.dal.ScoreDao;
import com.example.sondagecoincafe.dto.SurveyDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SurveyDtoServiceImpl implements SurveyDtoService {


//    private final QuestionDao questionDao;
//    private final ScoreDao scoreDao;
//    private final PeriodDao periodDao;
//
//    public SurveyDtoServiceImpl(QuestionDao questionDao, ScoreDao scoreDao, PeriodDao periodDao) {
//        this.questionDao = questionDao;
//        this.scoreDao = scoreDao;
//        this.periodDao = periodDao;
//    }
//
//    @Transactional
//    public void processSurvey(SurveyDto surveyDto) {
//
//        List<Question> questions = questionDao.findAll();
//
//        // 2) extraire les réponses du DTO
//        List<Integer> answers = List.of(
//                surveyDto.getQ1(), surveyDto.getQ2(), surveyDto.getQ3(), surveyDto.getQ4(), surveyDto.getQ5(),
//                surveyDto.getQ6(), surveyDto.getQ7(), surveyDto.getQ8(), surveyDto.getQ9(), surveyDto.getQ10()
//        );
//
//        LocalDateTime actualTimestamp = LocalDateTime.now();
//
//        // 3) période courante (mois en cours par ex.)
//        Period currentPeriod = getOrCreateCurrentPeriodByTimestamp(Timestamp.valueOf(actualTimestamp));
//
//        for (int i = 0; i < answers.size(); i++) {
//            Integer answer = answers.get(i);
//            if (answer == null || answer == 0) {
//                // NC → on n’incrémente rien
//                continue;
//            }
//
//            Question question = questions.get(i);
//            incrementTotalsForQuestion(question, answer, currentPeriod);
//        }
//    }
//
//    private List <Integer> getAnswers(SurveyDto surveyDto){
//
//        List<Integer> answers = List.of(
//                surveyDto.getQ1(), surveyDto.getQ2(), surveyDto.getQ3(), surveyDto.getQ4(), surveyDto.getQ5(),
//                surveyDto.getQ6(), surveyDto.getQ7(), surveyDto.getQ8(), surveyDto.getQ9(), surveyDto.getQ10()
//        );
//        return null;
//    }
//
//    private void incrementTotalsForQuestion(Question question, int answer, Period period) {
//
//        // score correspondant (1..5)
//        Score score = scoreDao.findByQuestionsAndScore(question, answer);
////                .orElseThrow(); // à adapter selon ta méthode
//
//        // incrémenter le nombre de votes pour cette note
//        score.setScoreVoteCount(score.getScoreVoteCount() + 1);
//
//        // incrémenter le total de votes de la question
//        question.setQuestionTotalVotes(question.getQuestionTotalVotes() + 1);
//        question.setQuestionTotalVotes(question.getQuestionTotalVotes() + 1);
//
//        // incrémenter la période
//        period.setPeriodTotalVotes(period.getPeriodTotalVotes() + 1);
//
//        // sauvegarde (cascade si configuré, sinon save les 3)
//        scoreDao.save(score);
//        questionDao.save(question);
//        periodDao.save(period);
//    }
//
//    private Period getOrCreateCurrentPeriodByTimestamp(Timestamp actualTimestamp) {
//        // ex : période par mois (année+mois)
//        return periodDao.findCurrentPeriodByTimestampPeriod(actualTimestamp)
//                .orElseGet(() -> {
//                    Period p = new Period();
//                    p.setTimestampPeriod(LocalDateTime.now());
//                    p.setPeriodTotalVotes(0);
//                    p.setPeriodTotalScore(0);
//                    return periodDao.save(p);
//                });
//    }
}

