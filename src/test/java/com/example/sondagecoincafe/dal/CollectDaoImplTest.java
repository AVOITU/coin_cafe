package com.example.sondagecoincafe.dal;

import com.example.sondagecoincafe.bo.Collect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class CollectDaoImplTest {

    @Autowired
    private CollectDao collectDao;


    @Test
    public void createCollectTest() {

        Collect collect = new Collect(1, "rue", "29", "Quimper");
        collectDao.create(collect);

    }
}