package com.example.sondagecoincafe.dal;

import com.example.sondagecoincafe.bo.Item;

import java.util.List;

public interface NoteDao {

    void create(Item item);
    Item read(int no_article);
    List<Item> findAll();
    String findName(int no_article);
}
