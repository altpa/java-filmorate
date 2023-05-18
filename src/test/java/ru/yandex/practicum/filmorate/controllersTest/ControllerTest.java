package ru.yandex.practicum.filmorate.controllersTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controllers.Controller;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.impl.Film;
import ru.yandex.practicum.filmorate.model.impl.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ControllerTest {
    Controller controller;
    Film film;
    User user;
    @BeforeEach
    public void beforeEach() {
        controller = new Controller();
        film = new Film(0, "Name", "Description", LocalDate.of(2000, 1,1), 100, 0);
        user = new User(0, "email@email.com", "login", "Name", LocalDate.of(1900, 1, 1));

    }

    @Test
    public void shouldAddFilm() {
        controller.add(film);
        film.setId(1);

        assertEquals(film, controller.get().get(0));
    }

    @Test
    public void shouldAddUser() {
        controller.add(user);
        user.setId(1);

        assertEquals(user, controller.get().get(0));
    }

    @Test
    public void shouldUpdateFilm() {
        controller.add(film);
        film.setId(1);
        Film newFilm = new Film(1, "New Name", "New Description", LocalDate.of(1990, 1,1), 100, 0);
        controller.add(newFilm);

        assertEquals(newFilm, controller.get().get(0));
    }

    @Test
    public void shouldUpdateUser() {
        controller.add(user);
        user.setId(1);
        User newUser = new User(1, "email@email.com", "New_login", "New Name", LocalDate.of(1990, 1, 1));
        controller.add(newUser);

        assertEquals(newUser, controller.get().get(0));
    }

    @Test
    public void shouldAddUserUnknown() {
        User newUser = new User(9999, "email@email.com", "New_login", "New Name", LocalDate.of(1990, 1, 1));
        Exception exception = assertThrows(ValidationException.class, () -> controller.add(newUser));
        String expectedMessage = "Такого id нет";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void shouldAddFilmUnknown() {
        Film newFilm = new Film(9999, "Name", "Description", LocalDate.of(2000, 1,1), 100, 0);
        Exception exception = assertThrows(ValidationException.class, () -> controller.add(newFilm));
        String expectedMessage = "Такого id нет";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
