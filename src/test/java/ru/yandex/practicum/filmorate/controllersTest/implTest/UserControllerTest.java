package ru.yandex.practicum.filmorate.controllersTest.implTest;

import lombok.Cleanup;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.filmorate.controllers.impl.UserController;
import ru.yandex.practicum.filmorate.model.impl.User;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class UserControllerTest {
    private Validator validator;

    @Before
    public void setUp() {
        @Cleanup ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void shouldAddNewUser() {
        User user = new User(2, "email@email.com", "login", "Name", LocalDate.of(1900, 1, 1));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void shouldAddNewUserWhenFailLogin() {
        User user = new User(2, "email@email.com", null, "Name", LocalDate.of(1900, 1, 1));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void shouldAddNewUserWhenFailEmail() {
        User user = new User(2, "email.com", "login", "Name", LocalDate.of(1900, 1, 1));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void shouldAddNewUserWhenFailBirthday() {
        User user = new User(2, "email@email.com", "login", "Name", LocalDate.of(2100, 1, 1));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void shouldAddNewUserWhenFailLoginWithSpaces() {
        User user = new User(2, "email@email.com", "log in", "Name", LocalDate.of(2000, 1, 1));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void shouldAddNewUserWhenEmptyName() {
        UserController userController = new UserController();

        User userNoName = new User(0, "email@email.com", "login", null, LocalDate.of(2000, 1, 1));
        userNoName = userController.create(userNoName);

        User userWithName = new User(1, "email@email.com", "login", "login", LocalDate.of(2000, 1, 1));

        assertEquals(userWithName, userNoName);
    }
}
