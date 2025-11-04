package com.example.sondagecoincafe.bll.impl;

import com.example.sondagecoincafe.bll.PeriodeService;
import com.example.sondagecoincafe.bo.Periode;

import java.util.List;

public class PeriodeServiceImpl implements PeriodeService {

    private PeriodeService periodeService;

    public PeriodeServiceImpl(PeriodeService periodeService) {
        this.periodeService = periodeService;
    }
}
