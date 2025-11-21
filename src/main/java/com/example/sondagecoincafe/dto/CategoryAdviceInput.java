package com.example.sondagecoincafe.dto;

import lombok.Data;

@Data
public class CategoryAdviceInput {
    private String label;
    private double value;

    public CategoryAdviceInput(){}

    public CategoryAdviceInput(String label, double value) {
        this.label = label;
        this.value = value;
    }
}


