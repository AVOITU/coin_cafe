package com.example.sondagecoincafe.controller.converter;

import com.example.sondagecoincafe.bll.CategoryService;
import com.example.sondagecoincafe.bo.Category;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToCategoryConverter implements Converter<String, Category> {

    private CategoryService categoryService;

    public StringToCategoryConverter(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public Category convert(String id) {
        int noCategory = Integer.parseInt(id);
        return categoryService.getCategoryById(noCategory);
    }
}
