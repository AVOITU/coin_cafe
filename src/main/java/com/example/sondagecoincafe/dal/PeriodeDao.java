package com.example.sondagecoincafe.dal;

import com.example.sondagecoincafe.bo.Periode;

import java.util.List;

public interface PeriodeDao {

    void create(Periode periode);
    Periode read(int no_article);
    List<Periode> findAll();
    String findName(int no_article);
}

