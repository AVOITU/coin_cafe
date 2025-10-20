package com.example.sondagecoincafe.bll;

import com.example.sondagecoincafe.bo.Category;

import java.util.List;

public interface CategoryService {

    List<Category> getAllCategories();
    Category getCategoryById(int id);
}
