package com.example.sondagecoincafe.bll;

import com.example.sondagecoincafe.dto.ResultsDto;
import org.springframework.stereotype.Service;

@Service
public interface ResultService {

    public ResultsDto getSurvey();
}
