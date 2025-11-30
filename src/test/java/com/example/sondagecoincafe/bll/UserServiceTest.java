package com.example.sondagecoincafe.bll;

import com.example.sondagecoincafe.bo.User;
import com.example.sondagecoincafe.exceptions.BusinessCode;
import com.example.sondagecoincafe.exceptions.BusinessException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // --------- helpers ---------

    private static User buildUser(String username,
                                  String rawPassword,
                                  String passwordConfirmation) {
        // adapte ce constructeur si ta BO a changé
        return new User(
                username,
                "Doe",
                "John",
                "john.doe+" + username + "@email.com",
                "0102030405",
                "1 rue de la Paix",
                "75000",
                "Paris",
                rawPassword,
                passwordConfirmation,
                0,
                false
        );
    }

    // --------- tests sécurité createUser ---------

    @Test
    void should_not_create_user_if_password_confirmation_is_ko() {
        // given
        String rawPassword = "user_password";
        User user = buildUser("user_ko", rawPassword, "different_password");

        // when
        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> userService.createUser(user)
        );

        // then
        assertThat(exception.getKeys())
                .contains(BusinessCode.VALIDATION_USER_PASSWORD_CONFIRMATION);
    }

    @Test
    void should_create_user_if_password_confirmation_is_ok() {
        // given
        String rawPassword = "user_password";
        User user = buildUser("userOK123", rawPassword, rawPassword);

        assertDoesNotThrow(() -> userService.createUser(user));
    }

    @Test
    void should_store_encoded_password_and_not_store_confirmation() {
        // given
        String rawPassword = "SuperPassword123!";
        String username = "userOK123";
        User user = buildUser(username, rawPassword, rawPassword);

        userService.createUser(user);

        Optional<User> maybeUser = userService.getByUsername(username);
        assertThat(maybeUser).isPresent();

        User stored = maybeUser.get();

        assertThat(stored.getPassword())
                .isNotEqualTo(rawPassword);
        assertThat(passwordEncoder.matches(rawPassword, stored.getPassword()))
                .isTrue();

        // 2) le champ transient de confirmation n’est pas rechargé
        assertThat(stored.getPasswordConfirmation()).isNull();
    }

    // --------- tests accès / recherche ---------

    @Test
    void should_find_user_by_username() {

        String username = "userOK123";
        User user = buildUser(username, "pwd12345", "pwd12345");
        userService.createUser(user);

        Optional<User> maybeUser = userService.getByUsername(username);

        assertThat(maybeUser).isPresent();
        assertThat(maybeUser.get().getUsername()).isEqualTo(username);
    }

    @Test
    void should_find_user_by_id_after_creation() {

        String username = "userOK123";
        User user = buildUser(username, "pwd12345", "pwd12345");
        userService.createUser(user);

        Optional<User> created = userService.getByUsername(username);
        assertThat(created).isPresent();
        Long id = created.get().getId();

        Optional<User> maybeUser = userService.getById(id);

        assertThat(maybeUser).isPresent();
        assertThat(maybeUser.get().getId()).isEqualTo(id);
        assertThat(maybeUser.get().getUsername()).isEqualTo(username);
    }

    @Test
    void should_return_empty_optional_when_user_not_found_by_username() {

        Optional<User> maybeUser = userService.getByUsername("unknown_user");

        assertThat(maybeUser).isEmpty();
    }
}
