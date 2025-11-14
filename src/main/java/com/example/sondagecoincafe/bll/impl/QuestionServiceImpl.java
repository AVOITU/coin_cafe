package com.example.sondagecoincafe.bll.impl;

import com.example.sondagecoincafe.bll.QuestionService;
import com.example.sondagecoincafe.bo.Question;
import com.example.sondagecoincafe.bo.Score;
import com.example.sondagecoincafe.configuration.AppConstants;
import com.example.sondagecoincafe.dal.QuestionDao;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Getter @Setter
public class QuestionServiceImpl implements QuestionService {

    private QuestionDao questionDao;

    public QuestionServiceImpl(QuestionDao questionDao){
        this.questionDao = questionDao;
    }

    @Override
    public List<Question> getDtoResults() {
        return questionDao.findAllWithRelations();
    }

    @Override
    public Map <Integer, Integer> getListVotesWithScore(List<Question> results) {

        Map <Integer, Integer> mapForPieCount = new HashMap<>(Map.of());

        if (results == null) return mapForPieCount;

        for (Question question : results){
            for (Score score : question.getScores()) {
                if (score.getScore() > 0){
                    int scoreValue = score.getScore();
                    int votes = score.getScoreVoteCount();
                    mapForPieCount.put(scoreValue, votes);
                }
            }
        }

        return mapForPieCount;
    }

    @Override
    public List <String> getTagsFromQuestionList(List <Question> questions){

        if (questions == null) return null;

        List <String> tagsFromList = new ArrayList<>();
        for (Question question : questions){
            String tag = question.getTag();
            tagsFromList.add(tag);
        }
        return tagsFromList;
    }

    @Override
    public List <Double> calculateAverageByTag(List <Question> questions){

        if (questions == null) return null;

        double averageQuestion = 0;
        List <Double> averagesByQuestion = new ArrayList<>();
        for (Question question : questions){
            averageQuestion = (double) question.getQuestionTotalScore() / question.getQuestionTotalVotes();
            averageQuestion = Math.round(averageQuestion * 10.0) / 10.0; // arrondi à 1 chiffre après la virgule
            averagesByQuestion.add(averageQuestion);
        }
        return averagesByQuestion;
    }

    @Override
    public List < Question> findAllQuestion (){
        return questionDao.findAll();
    }

    @Override
    public Question fillTotalsAndTagForQuestion(Map<String, Integer> questionsScore, int scoreQuestionSearched,
                                                Question question, String searchedQuestion,
                                                Map<String, String> questionCategoryMap, int questionIndex) {

        if ( question != null) {
            int newTotalScore = question.getQuestionTotalScore() + scoreQuestionSearched;
            question.setQuestionTotalScore(newTotalScore);

            if (questionsScore.containsKey(searchedQuestion)) {
                int newQuestionTotalVotes = question.getQuestionTotalVotes() + 1;
                question.setQuestionTotalVotes(newQuestionTotalVotes);

                String tag = questionCategoryMap.get(searchedQuestion);
                question.setTag(tag);
            }

            return question;
        }

        else {
            question = createNewQuestionIfQuestionNotPresent(questionCategoryMap, questionIndex, question);
        }

        return question;
    }

    @Override
    public Map<String, String> buildQuestionCategoryMap() {

        if (AppConstants.TAGS.size() != AppConstants.QUESTIONS_SENTENCES.length) {
            throw new IllegalStateException("Les listes TAGS et QUESTIONS_SENTENCES doivent avoir la même taille !");
        }

        Map<String, String> map = new LinkedHashMap<>();

        for (int i = 0; i < AppConstants.QUESTIONS_SENTENCES.length; i++) {
            map.put(AppConstants.QUESTIONS_SENTENCES[i], AppConstants.TAGS.get(i));
        }

        return map;
    }

    @Override
    public Question createNewQuestionIfQuestionNotPresent(Map<String, String> questionCategoryMap, int questionIndex,
                                                          Question question){

        List<String> questions = new ArrayList<>(questionCategoryMap.keySet());
        String questionSearched = questions.get(questionIndex);
        question = new Question();
        question.setQuestionText(questionSearched);
        question.setTag(questionCategoryMap.get(questionSearched));

        questionDao.save(question);
        return question;
    }
}
