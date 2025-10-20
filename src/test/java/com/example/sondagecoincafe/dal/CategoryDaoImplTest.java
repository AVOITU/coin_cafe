package com.example.sondagecoincafe.dal;

import com.example.sondagecoincafe.bo.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CategoryDaoImplTest {

    @Autowired
    private CategoryDao categoryDao;

    @Test
    public void findCategoryTest(){
        int id = 1;
        Category category = categoryDao.findCategoryById(id);
        assertThat(category.getNoCategory()).isEqualTo(1);
        assertThat(category.getName()).isEqualTo("Electronics");
    }
}
