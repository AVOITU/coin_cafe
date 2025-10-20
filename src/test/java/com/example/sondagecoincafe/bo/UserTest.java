package com.example.sondagecoincafe.bo;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidationUserOk() {
        User user = new User( "ES", "Etangsalé", "Tracy", "tracy.etangsale2024@campus-eni.fr", "rue Principale", "0298911314", "29140", "Quimper", "1234", "1234", 0, true);
        assertThat(validator.validate(user)).isEmpty();
    }

    @Test
    void testValidationErrorUsername() {
        String expectedErrorMessage = "Le pseudo doit être alphanumérique";
        String errorInUsername = "c_v";
        User user = new User( errorInUsername, "Etangsalé", "Tracy", "tracy.etangsale2024@campus-eni.fr", "rue Principale", "0298911314", "29140", "Quimper", "1234", "1234", 0, true);
//        assertThat(validator.validate(user)).isEmpty();
        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
        boolean hasExpectedMessage = constraintViolations.stream()
                .anyMatch(userConstraintViolation ->
                        userConstraintViolation.getMessage().contains(expectedErrorMessage));
//        assertThat(validator.validate(user)).hasSize(1);
        assertThat(hasExpectedMessage).isTrue();
    }

    @Test
    void testValidationErrorUsernameBlank() {
        String expectedErrorMessage = "ne doit pas être vide";
        String errorInUsername = "";
        User user = new User( errorInUsername, "Etangsalé", "Tracy", "tracy.etangsale2024@campus-eni.fr", "rue Principale", "0298911314", "29140", "Quimper", "1234", "1234", 0, true);
//        assertThat(validator.validate(user)).isEmpty();
        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
        boolean hasExpectedMessage = constraintViolations.stream()
                .anyMatch(userConstraintViolation ->
                        userConstraintViolation.getMessage().contains(expectedErrorMessage));
//        assertThat(validator.validate(user)).hasSize(1);
        assertThat(hasExpectedMessage).isTrue();
    }

    @Test
    void testValidationErrorfirstnameBlank() {
        String expectedErrorMessage = "ne doit pas être vide";
        String errorInFirstname = "";
        User user = new User( "ES", "Etangsalé", errorInFirstname, "tracy.etangsale2024@campus-eni.fr", "rue Principale", "0298911314", "29140", "Quimper", "1234", "1234", 0, true);
//        assertThat(validator.validate(user)).isEmpty();
        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
        boolean hasExpectedMessage = constraintViolations.stream()
                .anyMatch(userConstraintViolation ->
                        userConstraintViolation.getMessage().contains(expectedErrorMessage));
//        assertThat(validator.validate(user)).hasSize(1);
        assertThat(hasExpectedMessage).isTrue();
    }

    @Test
    void testValidationErrorCredit() {
        String expectedErrorMessage = "doit être supérieur ou égal à 0";
        Integer errorInCredit = -4;
        User user = new User( "ES", "Etangsalé", "Tracy", "tracy.etangsale2024@campus-eni.fr", "rue Principale", "0298911314", "29140", "Quimper", "1234", "1234", errorInCredit, true);
//        assertThat(validator.validate(user)).isEmpty();
        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
        boolean hasExpectedMessage = constraintViolations.stream()
                .anyMatch(userConstraintViolation ->
                        userConstraintViolation.getMessage().contains(expectedErrorMessage));
//        assertThat(validator.validate(user)).hasSize(1);
        assertThat(hasExpectedMessage).isTrue();
    }

    @Test
    void testValidationPasswordConfirmationBlank() {
        String expectedErrorMessage = "ne doit pas être vide";
        String blankPasswordConfirmation = "";
        User user = new User( "ES", "Etangsalé", "Tracy", "tracy.etangsale2024@campus-eni.fr", "rue Principale", "0298911314", "29140", "Quimper", "1234", blankPasswordConfirmation, 0, true);
//        assertThat(validator.validate(user)).isEmpty();
        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
        boolean hasExpectedMessage = constraintViolations.stream()
                .anyMatch(userConstraintViolation ->
                        userConstraintViolation.getMessage().contains(expectedErrorMessage));
//        assertThat(validator.validate(user)).hasSize(1);
        assertThat(hasExpectedMessage).isTrue();
    }


}
