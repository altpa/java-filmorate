package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.filmException.BadRequestFilmException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.validation.UserAndFilmValidation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FilmService {
    private final UserAndFilmValidation validation;
    private final FilmStorage films;
    private int counter = 0;


    @Autowired
    public FilmService(@Qualifier("filmDbStorage")FilmStorage filmStorage, UserAndFilmValidation validation) {
        this.validation = validation;
        this.films = filmStorage;
    }

    public Film addFilm(Film film) {
        validation.checkFilmRequest(film);
        counter++;
        film.setId(counter);
        films.addFilm(film);
        return films.getFilmById(counter);
    }

    public Film updateFilm(Film film) {
        validation.checkFilmRequest(film);
        validation.checkFilmsContainFilm(films.getFilms(), film);
        log.info("Будет обновлен фильм {}", film);
        films.updateFilm(film.getId(), film);
        return film;
    }

    public List<Film> getFilms() {
        return new ArrayList<Film>(films.getFilms().values());
    }

    public Film setLike(int id, int userId) {
        validation.checkFilmNull(films.getFilms(), films.getFilmById(id));
        films.setLike(id, userId);
        return films.getFilmById(id);
    }

    public Film deleteLike(int id, int userId) {
        validation.checkFilmNull(films.getFilms(), films.getFilmById(id));
        validation.checkFilmsHaveLike(userId, films.getFilmById(id));
        films.deleteLike(id, userId);
        return films.getFilmById(id);
    }

    public List<Film> getMostLiked(String countParam) {
        int count = Integer.parseInt(countParam);
        if (count < 0) {
            throw new BadRequestFilmException("count отрицательное");
        }
        return films.getFilms().values().stream()
                .sorted((film1, film2) -> film2.getLikes().size() - film1.getLikes().size())
                .limit(count)
                .collect(Collectors.toList());
    }

    public Film getFilmById(int id) {
        validation.checkFilmNull(films.getFilms(), films.getFilmById(id));
        return films.getFilmById(id);
    }
}
