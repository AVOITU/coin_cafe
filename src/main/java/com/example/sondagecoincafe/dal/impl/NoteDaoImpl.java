package com.example.sondagecoincafe.dal.impl;

import com.example.sondagecoincafe.bo.Item;
import com.example.sondagecoincafe.dal.NoteDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class NoteDaoImpl implements NoteDao {
    @Override
    public void create(Item item) {

    }

    @Override
    public Item read(int no_article) {
        return null;
    }

    @Override
    public List<Item> findAll() {
        return List.of();
    }

    @Override
    public String findName(int no_article) {
        return "";
    }
}
