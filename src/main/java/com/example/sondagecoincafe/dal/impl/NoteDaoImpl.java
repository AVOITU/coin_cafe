package com.example.sondagecoincafe.dal.impl;

import com.example.sondagecoincafe.bo.Item;
import com.example.sondagecoincafe.bo.Note;
import com.example.sondagecoincafe.dal.NoteDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class NoteDaoImpl implements NoteDao {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private JdbcTemplate jdbcTemplate;

    public NoteDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate, JdbcTemplate jdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.jdbcTemplate = jdbcTemplate;
    }

    static class NoteRowMapper implements RowMapper<Note> {

        @Override
        public Note mapRow(ResultSet rs, int rowNum) throws SQLException {
            Note note = new Note();
            note.setVoteCount(rs.getInt("vote_count"));
            note.setPercentageTotalVotes(rs.getInt("vote_count"));
            note.setTimestampNote(rs.getTimestamp("timestamp_note"));

            return note;
        }
    }
}
