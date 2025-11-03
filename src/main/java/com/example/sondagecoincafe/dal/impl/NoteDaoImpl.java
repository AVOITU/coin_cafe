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

    private static final String SELECT_ALL_PERIODES_VOTE_COUNT =
            "SELECT (vote_count) FROM NOTES";

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private JdbcTemplate jdbcTemplate;

    public NoteDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate, JdbcTemplate jdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Item item) {

    }

    @Override
    public List <Note> findAllVoteCount() {
        return jdbcTemplate.query(
                SELECT_ALL_PERIODES_VOTE_COUNT,
                new NoteRowMapper()
        );
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
