package com.example.sondagecoincafe.bo;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ItemTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidationUserOk() {
        Item item = new Item( "nom", "descr", LocalDate.now().plusDays(1) ,LocalDate.now().plusDays(1) , 5 , 0, "EC", null, new User(), null, null);
        assertThat(validator.validate(item)).isEmpty();
    }

    @Test
    void testValidationErrorUsername() {
        String expectedErrorMessage = "Le nom ne doit pas dépasser 100 caractères.";
        String moreThan100CharInUsername = "\"Le nom ne doit pas dépasser 100 caractères.\"frgyuregyufgsuyerfgu vfhbfsfgbqf sfhhfgshdqjhvfjhvfvsfgfsfhkhgrgsryuvyusfgyurskfgjhsvgbrbsyurgbsyubrgyu";
        Item item = new Item( moreThan100CharInUsername, "descr", LocalDate.now().plusDays(1) ,LocalDate.now().plusDays(1) , 5 , 0, "EC", null, new User(), null, null);
        Set<ConstraintViolation<Item>> constraintViolations = validator.validate(item);
        boolean hasExpectedMessage = constraintViolations.stream()
                .anyMatch(itemConstraintViolation ->
                        itemConstraintViolation.getMessage().contains(expectedErrorMessage));
        assertThat(hasExpectedMessage).isTrue();
    }

    @Test
    void testItemCreation() {
        User owner=new User();
        User buyer=new User();
        Category category=new Category();
        Collect collect = new Collect();
        Item item = new Item("appli","encheres", LocalDate.of(2024,6,6),LocalDate.of(2024,7,7),
                5,0,"Créé",category,owner,buyer, collect);
        assertThat(item.getNameItem()).isEqualTo("appli");
        assertThat(item.getDescription()).isEqualTo("encheres");
        assertThat(item.getStartingDate()).isEqualTo(LocalDate.of(2024, 6, 6));
        assertThat(item.getEndDate()).isEqualTo(LocalDate.of(2024,7,7));
        assertThat(item.getStartingPrice()).isEqualTo(5);
        assertThat(item.getSellPrice()).isEqualTo(0);
        assertThat(item.getstatus()).isEqualTo("Créé");
        assertThat(item.getOwner()).isEqualTo(owner);
        assertThat(item.getBuyer()).isEqualTo(buyer);
    }
}
