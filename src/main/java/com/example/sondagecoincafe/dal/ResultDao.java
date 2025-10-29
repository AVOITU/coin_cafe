package com.example.sondagecoincafe.dal;

import com.example.sondagecoincafe.bo.Category;
import com.example.sondagecoincafe.bo.Item;
import com.example.sondagecoincafe.bo.Result;

import java.util.List;

public interface ResultDao {

    List<Result> findAllResults();

    void create(Item item);
}
