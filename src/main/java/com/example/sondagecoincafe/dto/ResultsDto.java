package com.example.sondagecoincafe.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class ResultsDto {

    private double globalRating;
    private Map<Integer, Integer> pieCounts;
    private Timeline timeline;
    private Category categories;
    private Question byQuestion;

    // getters / setters

    @Data
    @NoArgsConstructor
    public static class Timeline {
        private List<String> labels;
        private List<Double> values;
    }

    @Data
    @NoArgsConstructor
    public static class Category {
        private List<String> labels;
        private List<Double> values;
        // getters / setters
    }

    @Data
    @NoArgsConstructor
    public static class Question {
        private List<String> labels;
        private List<Double> values;
        // getters / setters
    }
}
    
