package com.example.sondagecoincafe.dal;

import com.example.sondagecoincafe.bo.Item;
import com.example.sondagecoincafe.bo.Question;

import java.util.List;

public interface ResultDao {

    List<Question> findAllResults();

    void create(Item item);
}
