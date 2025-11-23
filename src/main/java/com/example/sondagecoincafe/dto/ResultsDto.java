package com.example.sondagecoincafe.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Data
@Getter @Setter
@NoArgsConstructor
public class ResultsDto {

    private double globalRating;
    private Map<Integer, Integer> pieCounts;
    private Timeline timeline;
//    private Category categories;
    private Question byQuestion;

    @Data
    @NoArgsConstructor
    @Getter @Setter
    public static class Timeline {
        private List<String> labels;
        private List<Double> values;
    }

//    @Data
//    @NoArgsConstructor
//    @Getter @Setter
//    public static class Category {
//        private List<String> labels;
//        private List<Double> values;
//    }

    @Data
    @NoArgsConstructor
    @Getter @Setter
    public static class Question {
        private List<String> labels;
        private List<Double> values;
    }
}
    
