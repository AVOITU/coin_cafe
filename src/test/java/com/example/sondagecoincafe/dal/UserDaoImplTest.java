package com.example.sondagecoincafe.dal;

import com.example.sondagecoincafe.bo.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UserDaoImplTest {

    @Autowired
    private UserDao userDao;

    @Test
    void test_select_by_userName() {
        Optional<User> result = userDao.selectByUsername("ES");

        Assertions.assertThat(result).isPresent();
        Assertions.assertThat(result.get().getUsername()).isEqualTo("ES");
    }

    @Test
    void test_sql_pseudo_unique() {

        //pseudo ES already in database

        User user = new User( "ES",
                "Etangsalé", "Tracy",
                "tracy.et@campus-eni.fr",
                "rue Principale", "0298911314",
                "29140", "Quimper", "1234", "1234", 0, true);
        DataAccessException exception = assertThrows(
                DataAccessException.class,
                () -> userDao.insertUser(user)
        );
        System.out.println("L'EXCEPTION EST "+exception.getMessage());
        assertTrue(exception.getMessage().contains("Violation de la contrainte UNIQUE KEY"));
    }

    @Test
    void test_sql_email_unique() {

        //tracy@campus already in database
        User user = new User( "AV",
                "Etangsalé", "Tracy",
                "tracy.etangsale2024@campus-eni.fr",
                "rue Principale","0298911314",
                "29140", "Quimper", "1234", "1234", 0, true);
        DataAccessException exception = assertThrows(
                DataAccessException.class,
                () -> userDao.insertUser(user)
        );
        System.out.println("L'EXCEPTION EST "+exception.getMessage());
        assertTrue(exception.getMessage().contains("Violation de la contrainte UNIQUE KEY"));
    }

    @Test
    void test_select_by_id() {
        Optional<User> result = userDao.getById(1);

        Assertions.assertThat(result).isPresent();
        Assertions.assertThat(result.get().getUsername()).isEqualTo("ES");
    }
}
