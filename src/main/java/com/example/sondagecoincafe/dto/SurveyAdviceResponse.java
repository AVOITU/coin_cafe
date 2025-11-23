package com.example.sondagecoincafe.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter @Setter
public class SurveyAdviceResponse {
    private int survey_id;
    private String provider;
    private String model;
    private String schema;
    private List<ResultItem> results;

    @Data
    @Getter @Setter
    public static class ResultItem {
        private String label;
        private double value;
        private AdviceDto adviceDto;
        private String error;
    }

    @Data
    @Getter @Setter
    public static class AdviceDto{
        private String priority;
        private String advice;
    }
}

