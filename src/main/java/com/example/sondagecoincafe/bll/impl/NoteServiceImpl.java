package com.example.sondagecoincafe.bll.impl;

import com.example.sondagecoincafe.bll.NoteService;
import com.example.sondagecoincafe.bo.Note;
import com.example.sondagecoincafe.bo.Result;
import com.example.sondagecoincafe.dal.NoteDao;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Getter @Setter
@NoArgsConstructor
public class NoteServiceImpl implements NoteService {

    private NoteDao noteDao;

    public NoteServiceImpl(NoteDao noteDao) {
        this.noteDao = noteDao;
    }

    @Override
    public List <Note> findAllVoteCount(){
        return noteDao.findAllVoteCount();
    }

    @Override
    public List <Integer> getVoteCounts(List <Note> note){
            List<Integer> voteCounts = note.stream()
                    .map(Note::getVoteCount)
                    .toList();
            return voteCounts;
    }
}
