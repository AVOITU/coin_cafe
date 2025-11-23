package com.example.sondagecoincafe.bll.impl;

import com.example.sondagecoincafe.bll.PeriodService;
import com.example.sondagecoincafe.bll.QuestionService;
import com.example.sondagecoincafe.bll.SurveyAdviceService;
import com.example.sondagecoincafe.bo.Period;
import com.example.sondagecoincafe.bo.Question;
import com.example.sondagecoincafe.dal.PeriodDao;
import com.example.sondagecoincafe.dal.QuestionDao;
import com.example.sondagecoincafe.dto.CategoryAdviceInput;
import com.example.sondagecoincafe.dto.ResultsDto;
import com.example.sondagecoincafe.dto.SurveyAdviceResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class SurveyAdviceServiceImpl implements SurveyAdviceService {

    private final RestTemplate restTemplate;
    private final PeriodService periodService;
    private final QuestionService questionService;

    public SurveyAdviceServiceImpl(RestTemplate restTemplate, PeriodDao periodDao, PeriodService periodService, QuestionDao questionDao, QuestionService questionService) {
        this.restTemplate = restTemplate;
        this.periodService = periodService;
        this.questionService = questionService;
    }

    @Override
    public SurveyAdviceResponse fetchSurveyAdvice(ResultsDto resultsDto) {
        String url = "http://localhost/survey_advice.php";

        List<CategoryAdviceInput> payload = buildCategoryInputs(resultsDto);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<List<CategoryAdviceInput>> entity = new HttpEntity<>(payload, headers);

        ResponseEntity<SurveyAdviceResponse> response =
                restTemplate.postForEntity(url, entity, SurveyAdviceResponse.class);

        return response.getBody();
    }
//
//    @Override
//    public List<CategoryAdviceInput> buildCategoryInputs(ResultsDto dto) {
//        List<CategoryAdviceInput> list = new ArrayList<>();
//
//        List<String> labels = dto.getCategories().getLabels();
//        List<Double> values = dto.getCategories().getValues();
//
//        for (int i = 0; i < labels.size(); i++) {
//            list.add(new CategoryAdviceInput(labels.get(i), values.get(i)));
//        }
//        return list;
//    }

//    @Override
//    public SurveyAdviceResponse fetchSurveyAdvice(ResultsDto resultsDto) {
//        return null;
//    }

    @Override
    public List<CategoryAdviceInput>
    buildCategoryInputs(ResultsDto resultsDto) {
        // VERSION RÉELLE (garde-la pour plus tard)
        List<CategoryAdviceInput> list = new ArrayList<>();

        List<String> labels = resultsDto.getByQuestion().getLabels();
        List<Double> values = resultsDto.getByQuestion().getValues();

        for (int i = 0; i < labels.size(); i++) {
            list.add(new CategoryAdviceInput(labels.get(i), values.get(i)));
        }
        return list;
    }

    @Override
    public SurveyAdviceResponse fetchSurveyAdviceMock() {
        SurveyAdviceResponse surveyAdviceResponse = new SurveyAdviceResponse();
        surveyAdviceResponse.setSurvey_id(1);
        surveyAdviceResponse.setProvider("mock");
        surveyAdviceResponse.setModel("mock-model");
        surveyAdviceResponse.setSchema("json_object");

        List<SurveyAdviceResponse.ResultItem> results = new ArrayList<>();

        SurveyAdviceResponse.ResultItem r1 = new SurveyAdviceResponse.ResultItem();
        r1.setLabel("Accueil");
        r1.setValue(3.8);
        SurveyAdviceResponse.AdviceDto adv = new SurveyAdviceResponse.AdviceDto();
        adv.setPriority("ciblee");
        adv.setAdvice("L’accueil est correct mais perfectible, shdfgcvydfuzsycvydv...");
        r1.setAdviceDto(adv);

        results.add(r1);

        SurveyAdviceResponse.ResultItem r2 = new SurveyAdviceResponse.ResultItem();
        r2.setLabel("Ambiance");
        r2.setValue(4.2);
        SurveyAdviceResponse.AdviceDto adv2 = new SurveyAdviceResponse.AdviceDto();
        adv2.setPriority("ciblée");
        adv2.setAdvice("L’accueil est correct mais perfectiblefegyvrvfgevgrgsv fgkegf gv grs" +
                " gvrsycv zgfy czg g cges gzgc gezcg zg cz zcv gzr gcvzyfvyuzrucveggfegvsgvgfvsgvgdvcvcgvfggdggv...");
        r2.setAdviceDto(adv2);
        results.add(r2);

        surveyAdviceResponse.setResults(results);
        return surveyAdviceResponse;
    }


    // --- VERSION MOCK POUR TESTS SANS DTO / PHP ---
    public List<CategoryAdviceInput> buildCategoryInputsMock() {
        List<CategoryAdviceInput> adviceInputArrayList = new ArrayList<>();
        adviceInputArrayList.add(new CategoryAdviceInput("Accueil", 3.8));
        adviceInputArrayList.add(new CategoryAdviceInput("Ambiance", 4.2));
        adviceInputArrayList.add(new CategoryAdviceInput("Accessibilité", 3.4));
        return adviceInputArrayList;
    }

    @Override
    public boolean consultIaForActualMonth(){
        Period period = periodService.getOrCreateCurrentPeriodByTimestamp();
        if (!period.isAnalysedByIa()){
            period.setAnalysedByIa(true);
            return false;
        }
        return true;
    }

    @Override
    public SurveyAdviceResponse processIaAdvice(List<Question> questions) {
        boolean isIaConsulted = consultIaForActualMonth();
        SurveyAdviceResponse iaResponse = null;
        if (!isIaConsulted) {
            iaResponse = fetchSurveyAdviceMock();
            questionService.fillAndSaveIaComments(questions, iaResponse);
            return iaResponse;
        } else {
            return questionService.buildSurveyAdviceFromDb(questions);
        }
    }
}

