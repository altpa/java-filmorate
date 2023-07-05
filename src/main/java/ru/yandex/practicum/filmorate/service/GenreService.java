package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.util.List;

@Slf4j
@Service
public class GenreService {
    private final GenreStorage genres;

    @Autowired
    public GenreService(GenreStorage genres) {
        this.genres = genres;
    }

    public List<Genre> getGenres() {
        return genres.getGenres();
    }

    public Genre getGenreById(int id) {
        return genres.getGenreById(id);
    }
}
