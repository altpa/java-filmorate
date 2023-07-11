package ru.yandex.practicum.filmorate.validation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.genreExceptions.DbGenreException;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

@Slf4j
@Component
public class GenreValidation {
    public void checkMpaExist(GenreStorage genres, int genreId) {
        if (!genres.getGenreIds().contains(genreId)) {
            throw new DbGenreException("Жанра с id=" + genreId + " нет");
        }
    }
}