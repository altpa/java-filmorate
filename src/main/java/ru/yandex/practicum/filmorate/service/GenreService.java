package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;
import ru.yandex.practicum.filmorate.validation.GenreValidation;

import java.util.List;

@Slf4j
@Service
public class GenreService {
    private final GenreStorage genres;
    private final GenreValidation validation;

    @Autowired
    public GenreService(GenreStorage genres, GenreValidation validation) {
        this.genres = genres;
        this.validation = validation;
    }

    public List<Genre> getGenres() {
        return genres.getGenres();
    }

    public Genre getGenreById(int id) {
        validation.checkMpaExist(genres, id);
        return genres.getGenreById(id);
    }
}
