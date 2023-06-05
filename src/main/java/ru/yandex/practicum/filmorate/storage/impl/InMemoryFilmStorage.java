package ru.yandex.practicum.filmorate.storage.impl;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> films = new HashMap<>();

    @Override
    public void addFilm(int id, Film film) {
        films.put(id, film);
    }

    @Override
    public Map<Integer, Film> getFilms() {
        return films;
    }
}