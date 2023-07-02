package ru.yandex.practicum.filmorate.storage.impl;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component("inMemoryFilmStorage")
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> films = new HashMap<>();

    @Override
    public Map<Integer, Film> getFilms() {
        return films;
    }

    @Override
    public void addFilm(Film film) {

    }

    @Override
    public void updateFilm(int id, Film film) {

    }

    @Override
    public Film getFilmById(int id) {
        return null;
    }

    @Override
    public void setLike(int id, int userId) {

    }

    @Override
    public void deleteLike(int id, int userId) {

    }
}