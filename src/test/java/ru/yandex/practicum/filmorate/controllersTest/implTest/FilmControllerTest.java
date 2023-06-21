package ru.yandex.practicum.filmorate.controllersTest.implTest;

import lombok.Cleanup;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.filmorate.controllers.FilmController;
import ru.yandex.practicum.filmorate.exceptions.filmException.NotFoundFilmException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.impl.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.validation.UserAndFilmValidation;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class FilmControllerTest {
    private Validator validator;

    @Before
    public void setUp() {
        @Cleanup ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void shouldAddNewFilm() {
        Film film = new Film(2, "Name", "Description", LocalDate.of(2000, 1,1), 100, 0);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void shouldAddNewFilmWhenFailName() {
        Film film = new Film(2, null, "Description", LocalDate.of(2000, 1,1), 100, 0);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void shouldAddNewFilmWhenFailDescription() {
        Film film = new Film(2, "Name", "Пятеро друзей ( комик-группа «Шарло»), " +
                "приезжают в город Бризуль. Здесь они хотят разыскать господина Огюста Куглова, который " +
                "задолжал им деньги, а именно 20 миллионов. о Куглов, который за время «своего отсутствия», стал " +
                "кандидатом Коломбани.", LocalDate.of(2000, 1,1), 100, 0);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void shouldAddNewFilmWhenFailDate() {
        Film film = new Film(2, "Name", "Description", LocalDate.of(1000, 1,1), 100, 0);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void shouldAddNewFilmWhenFailDuration() {
        Film film = new Film(2, "Name", "Description", LocalDate.of(2000, 1,1), -5, 0);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void shouldUpdateFilm() {
        FilmController filmController = new FilmController(new FilmService(new InMemoryFilmStorage(), new UserAndFilmValidation()));
        Film film = new Film(0, "Name", "Description", LocalDate.of(2000, 1,1), 100, 0);
        filmController.addFilm(film);
        Film newFilm = new Film(1, "New Name", "New Description", LocalDate.of(1990, 1,1), 100, 0);
        filmController.updateFilm(newFilm);

        assertEquals(newFilm, filmController.getFilms().get(0));
    }

    @Test
    public void shouldUpdateFilmUnknown() {
        FilmController filmController = new FilmController(new FilmService(new InMemoryFilmStorage(), new UserAndFilmValidation()));
        Film newFilm = new Film(9999, "Name", "Description", LocalDate.of(2000, 1,1), 100, 0);
        Exception exception = assertThrows(NotFoundFilmException.class, () -> filmController.updateFilm(newFilm));
        String expectedMessage = "Такого фильма нет, id: 9999";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}