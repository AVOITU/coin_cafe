package com.example.sondagecoincafe.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SurveyDto {

    @NotNull
    @Min(0) @Max(5)
    private Integer q1;
    @NotNull @Min(0) @Max(5)
    private Integer q2;
    @NotNull @Min(0) @Max(5)
    private Integer q3;
    @NotNull @Min(0) @Max(5)
    private Integer q4;
    @NotNull @Min(0) @Max(5)
    private Integer q5;

    @NotNull @Min(0) @Max(5)
    private Integer q6;
    @NotNull @Min(0) @Max(5)
    private Integer q7;
    @NotNull @Min(0) @Max(5)
    private Integer q8;
    @NotNull @Min(0) @Max(5)
    private Integer q9;
    @NotNull @Min(0) @Max(5)
    private Integer q10;
}
