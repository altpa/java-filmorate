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

@Slf4j
@Service
public class FilmService {
    private final UserAndFilmValidation validation;

    private final FilmStorage films;

    @Autowired
    public FilmService(@Qualifier("filmDbStorage")FilmStorage filmStorage, UserAndFilmValidation validation) {
        this.validation = validation;
        this.films = filmStorage;
    }

    public Film addFilm(Film film) {
        validation.checkFilmRequest(film);
        films.addFilm(film);
        return films.getFilmById(films.getMaxId());
    }

    public Film updateFilm(Film film) {
        validation.checkFilmRequest(film);
        validation.checkFilmsContainFilm(films.getFilms(), film);
        log.info("Будет обновлен фильм {}", film);
        films.updateFilm(film.getId(), film);
        return films.getFilmById(film.getId());
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
        return films.getMostLiked(countParam);
    }

    public Film getFilmById(int id) {
        validation.checkFilmNull(films.getFilms(), films.getFilmById(id));
        return films.getFilmById(id);
    }
}
