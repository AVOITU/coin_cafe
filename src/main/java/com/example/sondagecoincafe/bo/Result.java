package com.example.sondagecoincafe.bo;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter @Setter
@RequiredArgsConstructor
public class Result {

    @NonNull
    private String questionName;

    private String questionGlobalNotation;
    private String chatGptComments;
    private Timestamp timestampResult ;
    private int totalVoteCount ;
}
