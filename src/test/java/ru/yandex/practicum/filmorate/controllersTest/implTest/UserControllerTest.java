package ru.yandex.practicum.filmorate.controllersTest.implTest;

import lombok.Cleanup;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.filmorate.controllers.UserController;
import ru.yandex.practicum.filmorate.exceptions.userExceptions.NotFoundUserException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.impl.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.validation.UserAndFilmValidation;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class UserControllerTest {
    private Validator validator;
    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    @Before
    public void setUp() {
        @Cleanup ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void shouldAddNewUser() throws ParseException {
        User user = new User(2, "Name", "email@email.com", "login", formatter.parse("1900, 1, 1"));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void shouldAddNewUserWhenFailLogin() throws ParseException {
        User user = new User(2, "Name", "email@email.com", null,  formatter.parse("1900, 1, 1"));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void shouldAddNewUserWhenFailEmail() throws ParseException {
        User user = new User(2, "Name", "email.com", "login",  formatter.parse("1900, 1, 1"));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void shouldAddNewUserWhenFailBirthday() throws ParseException {
        User user = new User(2, "Name", "email@email.com", "login",  formatter.parse("2100, 1, 1"));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void shouldAddNewUserWhenFailLoginWithSpaces() throws ParseException {
        User user = new User(2, "Name", "email@email.com", "log in",  formatter.parse("2000, 1, 1"));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void shouldAddNewUserWhenEmptyName() throws ParseException {
        UserController userController = new UserController(new UserService(new InMemoryUserStorage(), new UserAndFilmValidation()));

        User userNoName = new User(0, "", "email@email.com", "login", formatter.parse("2000, 1, 1"));
        userNoName = userController.addUser(userNoName);

        User userWithName = new User(1, "login", "email@email.com", "login", formatter.parse("2000, 1, 1"));

        assertEquals(userWithName, userNoName);
    }

    @Test
    public void shouldUpdateUser() throws ParseException {
        UserController userController = new UserController(new UserService(new InMemoryUserStorage(), new UserAndFilmValidation()));
        User user = new User(0, "Name", "email@email.com", "login", formatter.parse("1900, 1, 1"));
        userController.addUser(user);
        User newUser = new User(1, "New Name", "email@email.com", "New_login", formatter.parse("1990, 1, 1"));
        userController.updateUser(newUser);

        assertEquals(newUser, userController.getUsers().get(0));
    }

    @Test
    public void shouldUpdateUserUnknown() throws ParseException {
        UserController userController = new UserController(new UserService(new InMemoryUserStorage(), new UserAndFilmValidation()));
        User newUser = new User(9999, "New Name", "email@email.com", "New_login", formatter.parse("1990, 1, 1"));
        Exception exception = assertThrows(NotFoundUserException.class, () -> userController.updateUser(newUser));
        String expectedMessage = "Такого пользователя нет, id: 9999";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
