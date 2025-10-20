package com.example.sondagecoincafe.dal.impl;

import com.example.sondagecoincafe.bo.Collect;
import com.example.sondagecoincafe.dal.CollectDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
public class CollectDaoImpl implements CollectDao {

    private final String INSERT_INTO = "INSERT INTO RETRAIT (rue, code_postal, ville, no_article)\n" +
            "    VALUES (:street, :zipCode, :city, :idItem)";
    private final String SELECT_BY_ID_ITEM = "SELECT * FROM RETRAIT WHERE no_article = ?";

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private JdbcTemplate jdbcTemplate;

    public CollectDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate, JdbcTemplate jdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Collect collect) {

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("idItem", collect.getId());
        namedParameters.addValue("street", collect.getStreet());
        namedParameters.addValue("zipCode", collect.getZipCode());
        namedParameters.addValue("city", collect.getCity());

        namedParameterJdbcTemplate.update(
                INSERT_INTO,
                namedParameters
        );
    }

    @Override
    public Optional<Collect> read(int idItem) {
        Collect collect = jdbcTemplate.queryForObject(
                SELECT_BY_ID_ITEM,
                new CollectRowMapper(),
                idItem);
        Optional<Collect> optionalCollect = Optional.of(collect);
        return null;
    }


    private class CollectRowMapper implements RowMapper<Collect> {
        @Override
        public Collect mapRow(ResultSet rs, int rowNum) throws SQLException {
            Collect collect = new Collect();
            collect.setId(rs.getInt("no_article"));
            collect.setStreet(rs.getString("rue"));
            collect.setZipCode(rs.getString("code_postal"));
            collect.setCity(rs.getString("ville"));

            return collect;
        }
    }
}
