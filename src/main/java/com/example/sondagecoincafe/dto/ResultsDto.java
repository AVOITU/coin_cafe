package com.example.sondagecoincafe.dto;

import java.util.List;
import java.util.Map;

public class ResultsDto {

    private double globalRating;
    private Map<Integer, Integer> pieCounts;
    private Timeline timeline;
    private Category categories;
    private Question byQuestion;

    public void setGlobalRating(double v) {
    }

    public void setPieCounts(Map<Integer,Integer> map) {
    }

    public void setTimeline(Timeline t) {
    }

    // getters / setters

    public static class Timeline {
        private List<String> labels;
        private List<Double> values;

        public void setLabels(List<String> strings) {
        }

        public void setValues(List<Double> doubles) {
        }
        // getters / setters
    }

    public static class Category {
        private List<String> labels;
        private List<Double> values;
        // getters / setters
    }

    public static class Question {
        private List<String> labels;
        private List<Double> values;
        // getters / setters
    }
}
    
