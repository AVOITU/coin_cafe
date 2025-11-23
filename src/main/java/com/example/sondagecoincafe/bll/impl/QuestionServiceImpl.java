package com.example.sondagecoincafe.bll.impl;

import com.example.sondagecoincafe.bll.QuestionService;
import com.example.sondagecoincafe.bo.Question;
import com.example.sondagecoincafe.bo.Score;
import com.example.sondagecoincafe.configuration.AppConstants;
import com.example.sondagecoincafe.dal.QuestionDao;
import com.example.sondagecoincafe.dto.SurveyAdviceResponse;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Getter
@Setter
public class QuestionServiceImpl implements QuestionService {

    private QuestionDao questionDao;

    public QuestionServiceImpl(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Override
    public List<Question> getDtoResults() {
        return questionDao.findAllWithRelations();
    }

    @Override
    public Map<Integer, Integer> getListVotesWithScore(List<Question> results) {

        Map<Integer, Integer> mapForPieCount = new HashMap<>(Map.of());

        if (results == null) return mapForPieCount;

        for (Question question : results) {
            for (Score score : question.getScores()) {
                if (score.getScore() > 0) {
                    int scoreValue = score.getScore();
                    int votes = score.getScoreVoteCount();
                    mapForPieCount.put(scoreValue, votes);
                }
            }
        }
        return mapForPieCount;
    }

    @Override
    public List<String> getTagsFromQuestionList(List<Question> questions) {

        if (questions == null) return null;

        List<String> tagsFromList = new ArrayList<>();
        for (Question question : questions) {
            String tag = question.getTag();
            tagsFromList.add(tag);
        }
        return tagsFromList;
    }

    @Override
    public List<Double> calculateAverageByTag(List<Question> questions) {

        if (questions == null) return null;

        double averageQuestion;
        List<Double> averagesByQuestion = new ArrayList<>();
        for (Question question : questions) {
            averageQuestion = (double) question.getQuestionTotalScore() / question.getQuestionTotalVotes();
            averageQuestion = Math.round(averageQuestion * 10.0) / 10.0; // arrondi à 1 chiffre après la virgule
            averagesByQuestion.add(averageQuestion);
        }
        return averagesByQuestion;
    }

    @Override
    public void fillAndSaveTotalsForQuestion(Question question, int responseScore) {

        int questionScore = question.getQuestionTotalScore();

            int newTotalScore = questionScore + responseScore;
            question.setQuestionTotalScore(newTotalScore);

            int newQuestionTotalVotes = question.getQuestionTotalVotes() + 1;
            question.setQuestionTotalVotes(newQuestionTotalVotes);

        questionDao.save(question);
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
    @Transactional
    public void checkAndAddQuestionsIfNotPresent(Map<String, String> questionCategoryMap, List<Question> questions) {
        for (String textQuestionToCheck : questionCategoryMap.keySet()) {
            boolean found = false;

            for (Question questionBdd : questions) {
                if (questionBdd.getQuestionText().equals(textQuestionToCheck)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                createNewQuestionIfQuestionNotPresent(questionCategoryMap, textQuestionToCheck);
            }
        }
    }

    @Override
    @Transactional
    public void createNewQuestionIfQuestionNotPresent(Map<String, String> questionCategoryMap, String textQuestion) {

        Question question = new Question();
        question.setQuestionText(textQuestion);
        question.setTag(questionCategoryMap.get(textQuestion));

        questionDao.save(question);
    }

    @Override
    @Transactional
    public void fillAndSaveIaComments(List<Question> questions, SurveyAdviceResponse surveyAdviceResponse) {

        if (surveyAdviceResponse == null || surveyAdviceResponse.getResults() == null) {
            return;
        }

        // indexer les résultats IA par label
        Map<String, SurveyAdviceResponse.ResultItem> byLabel = surveyAdviceResponse.getResults()
                .stream()
                .collect(Collectors.toMap(
                        SurveyAdviceResponse.ResultItem::getLabel,
                        Function.identity(),
                        (a, b) -> a   // en cas de doublon de label, on garde le premier
                ));

        for (Question question : questions) {
            // à adapter : tag ou questionText selon ce que tu utilises côté IA
            String key = question.getTag(); // ou question.getQuestionText()

            SurveyAdviceResponse.ResultItem label = byLabel.get(key);
            if (label == null || label.getAdviceDto() == null) {
                continue; // pas de conseil pour cette question
            }

            question.setChatgptComments(label.getAdviceDto().getAdvice());
            questionDao.save(question);
        }
    }

    @Override
    public SurveyAdviceResponse buildSurveyAdviceFromDb(List<Question> questions) {

        SurveyAdviceResponse response = new SurveyAdviceResponse();
        List<SurveyAdviceResponse.ResultItem> iaResponses = new ArrayList<>();

        for (Question question : questions) {
            // on ignore les questions sans commentaire IA
            if (question.getChatgptComments() == null || question.getChatgptComments().isBlank()) {
                continue;
            }

            SurveyAdviceResponse.ResultItem resultItem = getResultItem(question);

            iaResponses.add(resultItem);
        }

        response.setResults(iaResponses);
        return response;
    }

    private static SurveyAdviceResponse.ResultItem getResultItem(Question question) {
        SurveyAdviceResponse.ResultItem resultItem = new SurveyAdviceResponse.ResultItem();
        resultItem.setLabel(question.getTag());
        double value;
        if (question.getQuestionTotalVotes() != null && question.getQuestionTotalVotes() > 0) {
            value = (double) question.getQuestionTotalScore() / question.getQuestionTotalVotes();
            value = Math.round(value * 10.0) / 10.0;
            resultItem.setValue(value);
        }
        // error : null puisqu’on lit depuis la BDD
        resultItem.setError(null);
        SurveyAdviceResponse.AdviceDto adviceDto = new SurveyAdviceResponse.AdviceDto();
        adviceDto.setAdvice(question.getChatgptComments());
        resultItem.setAdviceDto(adviceDto);
        return resultItem;
    }
}