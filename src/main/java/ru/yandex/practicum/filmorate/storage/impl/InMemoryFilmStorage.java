package ru.yandex.practicum.filmorate.storage.impl;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.filmException.BadRequestFilmException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.validation.UserAndFilmValidation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component("inMemoryFilmStorage")
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> films = new HashMap<>();
    private int idCounter = 1;


    @Override
    public Map<Integer, Film> getFilms() {
        return films;
    }

    @Override
    public void addFilm(Film film) {
        film.setId(idCounter++);
    }

    @Override
    public void updateFilm(int id, Film film) {
        films.put(film.getId(), film);
    }

    @Override
    public Film getFilmById(int id) {
        return films.get(id);
    }

    @Override
    public void setLike(int id, int userId) {
        films.get(id).getLikes().add(userId);
    }

    @Override
    public void deleteLike(int id, int userId) {
        films.get(id).getLikes().remove(userId);
    }

    @Override
    public List<Film> getMostLiked(String countParam) {
        int count = Integer.parseInt(countParam);
        if (count < 0) {
            throw new BadRequestFilmException("count отрицательное");
        }
        return films.values().stream()
                .sorted((film1, film2) -> film2.getLikes().size() - film1.getLikes().size())
                .limit(count)
                .collect(Collectors.toList());
    }
}