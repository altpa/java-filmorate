package ru.yandex.practicum.filmorate.controllersTest.implTest;

import lombok.Cleanup;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.filmorate.controllers.UserController;
import ru.yandex.practicum.filmorate.exceptions.userExceptions.UpdateUserException;
import ru.yandex.practicum.filmorate.model.User;

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
        User user = new User(2, "Name", "email@email.com", "login", LocalDate.of(1900, 1, 1));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void shouldAddNewUserWhenFailLogin() {
        User user = new User(2, "Name", "email@email.com", null,  LocalDate.of(1900, 1, 1));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void shouldAddNewUserWhenFailEmail() {
        User user = new User(2, "Name", "email.com", "login",  LocalDate.of(1900, 1, 1));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void shouldAddNewUserWhenFailBirthday() {
        User user = new User(2, "Name", "email@email.com", "login",  LocalDate.of(2100, 1, 1));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void shouldAddNewUserWhenFailLoginWithSpaces() {
        User user = new User(2, "Name", "email@email.com", "log in",  LocalDate.of(2000, 1, 1));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void shouldAddNewUserWhenEmptyName() {
        UserController userController = new UserController();

        User userNoName = new User(0, null, "email@email.com", "login", LocalDate.of(2000, 1, 1));
        userNoName = userController.addUser(userNoName);

        User userWithName = new User(1, "login", "email@email.com", "login", LocalDate.of(2000, 1, 1));

        assertEquals(userWithName, userNoName);
    }

    @Test
    public void shouldUpdateUser() {
        UserController userController = new UserController();
        User user = new User(0, "Name", "email@email.com", "login", LocalDate.of(1900, 1, 1));
        userController.addUser(user);
        User newUser = new User(1, "New Name", "email@email.com", "New_login", LocalDate.of(1990, 1, 1));
        userController.updateUser(newUser);

        assertEquals(newUser, userController.getUsers().get(0));
    }

    @Test
    public void shouldUpdateUserUnknown() {
        UserController userController = new UserController();
        User newUser = new User(9999, "New Name", "email@email.com", "New_login", LocalDate.of(1990, 1, 1));
        Exception exception = assertThrows(UpdateUserException.class, () -> userController.updateUser(newUser));
        String expectedMessage = "Невозможно обновить";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
