package com.example.sondagecoincafe.dal.impl;

import com.example.sondagecoincafe.bo.Item;
import com.example.sondagecoincafe.bo.Periode;
import com.example.sondagecoincafe.dal.PeriodeDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PeriodeDaoImpl implements PeriodeDao {

    private static final String SELECT_ALL_PERIODES =
            "SELECT (periode_global_notation, periode_timestamp) FROM PERIODES";

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private JdbcTemplate jdbcTemplate;

    public PeriodeDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate, JdbcTemplate jdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public void create(Periode periode) {

    }

    @Override
    public Periode read(int no_article) {
        return null;
    }

    @Override
    public List<Periode> findAll() {
        return jdbcTemplate.query(
                SELECT_ALL_PERIODES,
                new PeriodeRowMapper()
        );
    }

    @Override
    public String findName(int no_article) {
        return "";
    }

    static class PeriodeRowMapper implements RowMapper<Periode> {


        @Override
        public Periode mapRow(ResultSet rs, int rowNum) throws SQLException {
            Periode periode = new Periode();
            periode.setPeriodeTimestamp(rs.getTimestamp("periode_name"));
            periode.setPeriodeGlobalNotation(rs.getFloat("periode_global_notation"));

            return periode;
        }
    }
}
