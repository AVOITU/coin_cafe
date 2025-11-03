package com.example.sondagecoincafe.bo;

import lombok.*;

import java.sql.Timestamp;

@Getter @Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Result {

    @NonNull
    private String questionName;

    private float questionGlobalNotation;
    private String chatGptComments;
    private Timestamp timestampResult ;
    private int totalVoteCount ;

}
