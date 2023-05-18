package ru.yandex.practicum.filmorate.controllersTest.implTest;

import lombok.Cleanup;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.filmorate.model.impl.Film;
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
    public void shouldAddNewUser() {
        Film film = new Film(2, "Name", "Description", LocalDate.of(2000, 1,1), 100, 0);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void shouldAddNewUserWhenFailName() {
        Film film = new Film(2, null, "Description", LocalDate.of(2000, 1,1), 100, 0);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void shouldAddNewUserWhenFailDescription() {
        Film film = new Film(2, "Name", "Пятеро друзей ( комик-группа «Шарло»), " +
                "приезжают в город Бризуль. Здесь они хотят разыскать господина Огюста Куглова, который " +
                "задолжал им деньги, а именно 20 миллионов. о Куглов, который за время «своего отсутствия», стал " +
                "кандидатом Коломбани.", LocalDate.of(2000, 1,1), 100, 0);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void shouldAddNewUserWhenFailDate() {
        Film film = new Film(2, "Name", "Description", LocalDate.of(1000, 1,1), 100, 0);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void shouldAddNewUserWhenFailDuration() {
        Film film = new Film(2, "Name", "Description", LocalDate.of(2000, 1,1), -5, 0);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty());
    }
}
