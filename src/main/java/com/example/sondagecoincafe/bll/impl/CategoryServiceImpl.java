package com.example.sondagecoincafe.bll.impl;

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
