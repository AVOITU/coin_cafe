package com.example.sondagecoincafe.dal.impl;

import com.example.sondagecoincafe.bo.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
public class UserDaoImpl implements com.example.sondagecoincafe.dal.UserDao {

    private final String SELECT_BY_USERNAME = "SELECT * FROM UTILISATEURS WHERE pseudo = ?";
    private final String SELECT_BY_ID = "SELECT * FROM UTILISATEURS WHERE no_utilisateur = ?";
    private final String INSERT = "INSERT INTO UTILISATEURS (pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur) " +
            "VALUES (:username, :name, :firstname, :email, :phone, :street, :zipCode, :city, :password, :credit, :admin)";

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private JdbcTemplate jdbcTemplate;

    public UserDaoImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Optional<User> getById(int id) {
        User user = jdbcTemplate.queryForObject(
                SELECT_BY_ID,
                new UserRowMapper(),
                id);
        Optional<User> optUser = Optional.of(user);
        return optUser;
    }

    @Override
    public Optional<User> selectByUsername(String username) {
        User user = jdbcTemplate.queryForObject(
                SELECT_BY_USERNAME,
                new UserRowMapper(),
                username);
        Optional<User> optUser = Optional.of(user);
        return optUser;

    }


    @Override
    public void insertUser(User user) {
        try {
            MapSqlParameterSource namedParameters = new MapSqlParameterSource();
            namedParameters.addValue("username", user.getUsername());
            namedParameters.addValue("name", user.getName());
            namedParameters.addValue("firstname", user.getFirstname());
            namedParameters.addValue("email", user.getEmail());
            namedParameters.addValue("phone", user.getPhone());
            namedParameters.addValue("street", user.getStreet());
            namedParameters.addValue("zipCode", user.getZipCode());
            namedParameters.addValue("city", user.getCity());
            namedParameters.addValue("password", user.getPassword());
            namedParameters.addValue("credit", user.getCredit());
            namedParameters.addValue("admin", user.getAdmin());

            this.namedParameterJdbcTemplate.update(
                    INSERT,
                    namedParameters
            );

        } catch (DataAccessException e){
            System.err.println("Error inserting user data: " + e.getMessage());
            throw e;
        }
    }

    private class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setNo_user(rs.getInt("no_utilisateur"));
            user.setUsername(rs.getString("pseudo"));
            user.setName(rs.getString("nom"));
            user.setFirstname(rs.getString("prenom"));
            user.setEmail(rs.getString("email"));
            user.setPhone(rs.getString("telephone"));
            user.setStreet(rs.getString("rue"));
            user.setZipCode(rs.getString("code_postal"));
            user.setCity(rs.getString("ville"));
            user.setPassword(rs.getString("mot_de_passe"));
            user.setCredit(rs.getInt("credit"));
            user.setAdmin(rs.getBoolean("administrateur"));

            return user;
        }
    }
}
