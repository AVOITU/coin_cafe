package com.example.sondagecoincafe.bo;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CollectTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

//    @Test
//    void testValidationUserOk() {
//        Collect collect = new Collect(5, "rue de la Gare", "29000", "Quimper");
//        assertThat(validator.validate(collect)).isEmpty();
//    }

}
