package com.example.sondagecoincafe.dal.impl;

import com.example.sondagecoincafe.bo.*;
import com.example.sondagecoincafe.dal.QuestionDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class QuestionDaoImpl implements QuestionDao {

    private static final String SELECT_ALL_RESULTS_NAMES_NOTATIONS =
            "SELECT (question_name, question_global_notation) FROM RESULTS";

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private JdbcTemplate jdbcTemplate;

    public QuestionDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate, JdbcTemplate jdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Item item) {

    }

    public List<Question> findAllResults(){
        return jdbcTemplate.query(
                SELECT_ALL_RESULTS_NAMES_NOTATIONS,
                new ResultRowMapper()
        );
    }

    static class ResultRowMapper implements RowMapper<Question> {


        @Override
        public Question mapRow(ResultSet rs, int rowNum) throws SQLException {
            Question result = new Question();
            result.setQuestionName(rs.getString("question_name"));
            result.setQuestionGlobalNotation(rs.getFloat("question_global_notation"));
            result.setChatGptComments(rs.getString("chat_gpt_comments"));
            result.setTimestampResult(rs.getTimestamp("timestamp_result"));
            result.setTotalVoteCount(rs.getInt("total_vote_count"));

            return result;
        }
    }
}
