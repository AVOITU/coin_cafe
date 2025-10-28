package com.example.sondagecoincafe.bo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@RequiredArgsConstructor
public class Note {

    private int voteCount;
    private int percentageTotalVotes;
    private Timestamp timestampNote;
}
