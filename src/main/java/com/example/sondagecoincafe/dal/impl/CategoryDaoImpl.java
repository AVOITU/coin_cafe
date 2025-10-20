package com.example.sondagecoincafe.dal.impl;

import com.example.sondagecoincafe.bo.Category;
import com.example.sondagecoincafe.dal.CategoryDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CategoryDaoImpl implements CategoryDao {

    private JdbcTemplate jdbcTemplate;

    public CategoryDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Category findCategoryById(int noCategory) {

        String sql= "SELECT no_categorie, libelle FROM CATEGORIES WHERE no_categorie=?;";

        return jdbcTemplate.queryForObject(sql, new CategoryRowMapper(), noCategory);
        }

    @Override
    public List<Category> findAllCategories() {
        String sql= "SELECT no_categorie, libelle FROM CATEGORIES;";
        return jdbcTemplate.query(sql, new CategoryRowMapper());
        }

    private class CategoryRowMapper implements RowMapper<Category> {
        @Override
        public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
            Category category = new Category();
            category.setNoCategory(rs.getInt("no_categorie"));
            category.setName(rs.getString("libelle"));
            return category;
        }
    }
}


