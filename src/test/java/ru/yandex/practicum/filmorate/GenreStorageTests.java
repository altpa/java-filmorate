package ru.yandex.practicum.filmorate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class GenreStorageTests {

    @Autowired
    private GenreStorage genreStorage;

    @Test
    public void testGenres() {
        assertNotNull(genreStorage);
    }

    @Test
    public void getGenreById() {
        assertThat(genreStorage.getGenreById(1)).hasFieldOrPropertyWithValue("name", "Комедия");
        assertThat(genreStorage.getGenreById(2)).hasFieldOrPropertyWithValue("name", "Драма");
        assertThat(genreStorage.getGenreById(3)).hasFieldOrPropertyWithValue("name", "Мультфильм");
        assertThat(genreStorage.getGenreById(4)).hasFieldOrPropertyWithValue("name", "Триллер");
        assertThat(genreStorage.getGenreById(5)).hasFieldOrPropertyWithValue("name", "Документальный");
        assertThat(genreStorage.getGenreById(6)).hasFieldOrPropertyWithValue("name", "Боевик");
    }

    @Test
    public void getGenres() {
        assertThat(genreStorage.getGenres().size()).isEqualTo(6);
    }

    @Test
    public void getGenreIds() {
        assertThat(genreStorage.getGenreIds().size()).isEqualTo(6);
    }
}