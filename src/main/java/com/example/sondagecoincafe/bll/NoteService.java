package com.example.sondagecoincafe.bll;

import com.example.sondagecoincafe.bo.Note;

import java.util.List;

public interface NoteService {

    List<Note> findAllVoteCount();

    List <Integer> getVoteCounts(List <Note> note);
}
