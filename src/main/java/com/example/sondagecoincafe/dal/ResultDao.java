package com.example.sondagecoincafe.dal;

import com.example.sondagecoincafe.bo.Category;
import com.example.sondagecoincafe.bo.Item;

import java.util.List;

public interface ResultDao {

    Category findCategoryById(int noCategory);
    void create(Item item);
    Item read(int no_article);
    List<Item> findAll();
    String findName(int no_article);
}
