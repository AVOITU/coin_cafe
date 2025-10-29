package com.example.sondagecoincafe.dal;

import com.example.sondagecoincafe.bo.Item;
import com.example.sondagecoincafe.bo.Note;

import java.util.List;

public interface NoteDao {

    void create(Item item);
    List <Note> findAllVoteCount();
}
