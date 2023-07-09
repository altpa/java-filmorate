package ru.yandex.practicum.filmorate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.yandex.practicum.filmorate.storage.RatingStorage;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RatingStorageTests {

    @Autowired
    RatingStorage ratingStorage;

    @Test
    public void testRating() {
        assertNotNull(ratingStorage);
    }

    @Test
    public void getRatingById() {
        assertThat(ratingStorage.getRatingById(1)).hasFieldOrPropertyWithValue("name", "G");
        assertThat(ratingStorage.getRatingById(2)).hasFieldOrPropertyWithValue("name", "PG");
        assertThat(ratingStorage.getRatingById(3)).hasFieldOrPropertyWithValue("name", "PG-13");
        assertThat(ratingStorage.getRatingById(4)).hasFieldOrPropertyWithValue("name", "R");
        assertThat(ratingStorage.getRatingById(5)).hasFieldOrPropertyWithValue("name", "NC-17");
    }

    @Test
    public void getGenres() {
        assertThat(ratingStorage.getRatings().size()).isEqualTo(5);
    }

    @Test
    public void getMpaIds() {
        assertThat(ratingStorage.getMpaIds().size()).isEqualTo(5);
    }
}
