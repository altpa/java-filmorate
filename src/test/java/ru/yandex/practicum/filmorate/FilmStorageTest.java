package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql({"/schema.sql", "/test-data.sql"})
class FilmStorageTest {
    @Autowired
    @Qualifier("filmDbStorage")
    private FilmStorage filmStorage;

    @Test
    public void testFilmStorage() {
        assertNotNull(filmStorage);
    }

    @Test
    public void getFilms() {
        assertThat(filmStorage.getFilms().size()).isEqualTo(2);
    }

    @Test
    public void addFilm() {
        Film newFilm = new Film(0, "New Film Name", "New Description",
                LocalDate.of(1900, 10, 10), 100, 2, null, new Mpa(1, "G"),
                new ArrayList<>(Arrays.asList(new Genre(1, "Комедия"), new Genre(2, "Драма"))));
        filmStorage.addFilm(newFilm);
        assertThat(filmStorage.getFilmById(3)).get().hasFieldOrPropertyWithValue("name", "New Film Name");
    }

    @Test
    public void updateFilm() {
        Film updatedFilm = new Film(1, "Updated Film Name", "Updated Description",
                LocalDate.of(2000, 11, 11), 100, 2, null, new Mpa(1, "G"),
                new ArrayList<>(List.of(new Genre(1, "Комедия"))));
        filmStorage.updateFilm(1, updatedFilm);
        assertThat(filmStorage.getFilmById(1)).get().hasFieldOrPropertyWithValue("name", "Updated Film Name");
    }

    @Test
    public void getFilmById() {
        assertThat(filmStorage.getFilmById(1)).get().hasFieldOrPropertyWithValue("name", "Film Name1");
    }

    @Test
    public void setLike() {
        filmStorage.setLike(1, 3);
        filmStorage.setLike(1, 2);
        filmStorage.setLike(2, 3);
        assertThat(filmStorage.getMostLiked("3").get(0)).hasFieldOrPropertyWithValue("name", "Film Name1");
    }

    @Test
    public void deleteLike() {
        filmStorage.deleteLike(1, 3);
        filmStorage.deleteLike(1, 2);
        assertThat(filmStorage.getMostLiked("3").get(0)).hasFieldOrPropertyWithValue("name", "Film Name2");
    }

    @Test
    public void getMostLiked() {
        assertThat(filmStorage.getMostLiked("3").get(0)).hasFieldOrPropertyWithValue("name", "Film Name1");
    }

    @Test
    public void getMaxId() {
        assertThat(filmStorage.getMaxId()).isEqualTo(2);
    }
}
