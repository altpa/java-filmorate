package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final Map<Integer, Film> films;
    private int idCounter = 1;

    @Autowired
    public FilmService(FilmStorage filmStorage, UserAndFilmValidation validation) {
        this.validation = validation;
        films = filmStorage.getFilms();
    }

    public Film addFilm(Film film) {
        validation.checkFilmRequest(film);
        film.setId(idCounter++);
        films.put(film.getId(), film);
        return film;
    }

    public Film updateFilm(Film film) {
        validation.checkFilmRequest(film);
        validation.checkFilmsContainFilm(films, film);
        log.info("Будет обновлен фильм {}", films.get(film.getId()));
        films.put(film.getId(), film);
        return film;
    }

    public List<Film> getFilms() {
        return new ArrayList<>(films.values());
    }

    public Film setLike(int id, int userId) {
        validation.checkFilmNull(films, films.get(id));
        films.get(id).getLikes().add(userId);
        return films.get(id);
    }

    public Film deleteLike(int id, int userId) {
        validation.checkFilmNull(films, films.get(id));
        validation.checkFilmsHaveLike(userId, films.get(id));
        films.get(id).getLikes().remove(userId);
        return films.get(id);
    }

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

    public Film getFilmById(int id) {
        validation.checkFilmNull(films, films.get(id));
        return films.get(id);
    }
}
