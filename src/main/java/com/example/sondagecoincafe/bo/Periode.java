package com.example.sondagecoincafe.bo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter @Setter
@RequiredArgsConstructor
public class Periode {

    private Timestamp periodeTimestamp;
    private float periodeGlobalNotation;
}
