package com.example.sondagecoincafe.bll;

import com.example.sondagecoincafe.bo.Note;
import com.example.sondagecoincafe.bo.Periode;
import com.example.sondagecoincafe.bo.Result;
import com.example.sondagecoincafe.dto.ResultsDto;
import org.springframework.stereotype.Service;

@Service
public interface ResultService {

    Note getDtoNotes();

    Periode getDtoPeriodes();

    Result getDtoResults();

    ResultsDto fillResultsDto();
}
