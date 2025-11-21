package com.example.sondagecoincafe.bll.impl;

import com.example.sondagecoincafe.bll.SurveyAdviceService;
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

    public SurveyAdviceServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public SurveyAdviceResponse fetchSurveyAdvice(ResultsDto dto) {
        String url = "http://localhost/survey_advice.php";

        List<CategoryAdviceInput> payload = buildCategoryInputs(dto);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<List<CategoryAdviceInput>> entity = new HttpEntity<>(payload, headers);

        ResponseEntity<SurveyAdviceResponse> response =
                restTemplate.postForEntity(url, entity, SurveyAdviceResponse.class);

        return response.getBody();
    }

    @Override
    public List<CategoryAdviceInput> buildCategoryInputs(ResultsDto dto) {
        List<CategoryAdviceInput> list = new ArrayList<>();

        List<String> labels = dto.getCategories().getLabels();
        List<Double> values = dto.getCategories().getValues();

        for (int i = 0; i < labels.size(); i++) {
            list.add(new CategoryAdviceInput(labels.get(i), values.get(i)));
        }
        return list;
    }

    @Override
    public String callPhpAdvice(ResultsDto dto) {
        String url = "http://localhost/survey_advice.php";

        List<CategoryAdviceInput> payload = buildCategoryInputs(dto);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<List<CategoryAdviceInput>> entity = new HttpEntity<>(payload, headers);

        ResponseEntity<String> response =
                restTemplate.postForEntity(url, entity, String.class);

        return response.getBody();
    }
}

