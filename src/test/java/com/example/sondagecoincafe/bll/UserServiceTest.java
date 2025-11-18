package com.example.sondagecoincafe.bll;

import com.example.sondagecoincafe.bo.User;
import com.example.sondagecoincafe.exceptions.BusinessCode;
import com.example.sondagecoincafe.exceptions.BusinessException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;


    @Test
    void should_not_create_user_if_password_confirmation_is_ko() {
        String userPassword = "user_password";
        String passwordConfirmation = "different_password";

        User user = getUser(userPassword, passwordConfirmation);
        String expectedMessage = BusinessCode.VALIDATION_USER_PASSWORD_CONFIRMATION;

        BusinessException exception = org.junit.jupiter.api.Assertions.assertThrows(BusinessException.class, () -> {
            userService.createUser(user);
        });

        assertTrue(exception.getKeys().stream().anyMatch(key -> key.equals(expectedMessage)));
    }

    @Test
    void should_create_user_if_password_confirmation_is_ok() {
        String userPassword = "user_password";
        User user = getUser(userPassword, userPassword);
        assertDoesNotThrow(() -> userService.createUser(user));
    }

    private static User getUser(String userPassword, String passwordConfirmation) {
        return new User("username", "name", "firstname", "email@email.com", "street", "phone", "zipCode", "city", userPassword, passwordConfirmation, 0, false);
    }

    @Test
    void should_find_user_by_username() {
        String username = "ES";
        String expectedFirstName = "Tracy";
        Optional<User> maybeUser = userService.getByUsername(username);

        Assertions.assertThat(maybeUser.isPresent()).isTrue();
        Assertions.assertThat(maybeUser.get().getFirstname()).isEqualTo(expectedFirstName);
    }

    @Test
    void should_find_user_by_id() {
        Long id = 1L;
        String expectedFirstName = "Tracy";
        Optional<User> maybeUser = userService.getById(id);

        Assertions.assertThat(maybeUser.isPresent()).isTrue();
        Assertions.assertThat(maybeUser.get().getFirstname()).isEqualTo(expectedFirstName);
    }
}

