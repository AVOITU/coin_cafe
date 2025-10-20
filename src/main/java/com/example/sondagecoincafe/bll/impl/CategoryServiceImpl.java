package com.example.sondagecoincafe.bll.impl;

import com.example.sondagecoincafe.bll.CategoryService;
import com.example.sondagecoincafe.bo.Category;
import com.example.sondagecoincafe.dal.CategoryDao;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryDao categoryDao;

    public CategoryServiceImpl(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Override
    public List<Category> getAllCategories() {return categoryDao.findAllCategories();}

    @Override
    public Category getCategoryById(int idCategoryFront) {
        return categoryDao.findCategoryById(idCategoryFront);
    }
}
