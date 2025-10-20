package com.example.sondagecoincafe.dal;

import com.example.sondagecoincafe.bo.Category;

import java.util.List;

public interface CategoryDao {

    Category findCategoryById(int noCategory);
    public List<Category> findAllCategories();
}
