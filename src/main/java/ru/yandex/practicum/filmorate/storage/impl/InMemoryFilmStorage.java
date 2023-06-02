package ru.yandex.practicum.filmorate.storage.impl;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import ru.yandex.practicum.filmorate.exceptions.filmException.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class InMemoryFilmStorage implements FilmStorage {
    FilmService filmService;
    private final Map<Integer, Film> films = new HashMap<>();
    private int idCounter = 1;

    public InMemoryFilmStorage() {
        filmService = new FilmService();
    }

    @Override
    public Film addFilm(Film film) {
        if (film == null) {
            throw new AddFilmException("Пустой запрос");
        }
        log.info("Получен POST запрос для добавления фильма: {}", film);
        film.setId(idCounter++);
        films.put(film.getId(), film);
        log.info("Фильм добавлен: {}", film);
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        if (film == null) {
            throw new UpdateFilmException("Пустой запрос");
        }
        if (!films.containsKey(film.getId())) {
            throw new UpdateFilmException("Такого фильма нет id: " + film.getId() + ", нечего обновлять");
        }
        log.info("Получен PUT запрос для обновления фильма: {}", film);
        return filmService.checkFilm(films, film);
    }

    @Override
    public List<Film> getFilms() {
        log.info("Получен GET запрос для списка фильмов");
        val answer = new ArrayList<>(films.values());
        log.info("Отправлен список фильмов: {}", answer);
        return answer;
    }

    @Override
    public Film setLike(int id, int userId) {
        if (!films.containsKey(id)) {
            throw new SetLikeException("Фильма с id: " + id + " нет");
        }
        log.info("Получен PUT запрос для добавления лайка фильму {}", films.get(id));
        films.get(id).getLikes().add(userId);
        log.info("Фильму {} добавлен лайк", films.get(id));
        return films.get(id);
    }

    @Override
    public Film deleteLike(int id, int userId) {
        if (!films.containsKey(id)) {
            throw new DeleteLikeException("Фильма с id: " + id + " нет");
        }
        if (!films.get(id).getLikes().contains(userId)) {
            throw new DeleteLikeException("У фильма нет лайка пользователя id:" + userId);
        }
        log.info("Получен DELETE запрос для удаления лайка у фильма {}", films.get(id));
        films.get(id).getLikes().remove(userId);
        log.info("Фильму {} удален лайк", films.get(id));
        return films.get(id);
    }

    @Override
    public List<Film> getMostLiked(String countParam) {
        int count = Integer.parseInt(countParam);
        log.info("Получен GET запрос для получения списка первых {} самых залайканых фильмов", count);
        if (count < 0) {
            throw new GetMostLikedException("count отрицательное");
        }
        List<Film> answer = films.values().stream()
                .sorted((film1, film2) -> film2.getLikes().size() - film1.getLikes().size())
                .limit(count)
                .collect(Collectors.toList());
        log.info("Отправлен список фильмов: {}", answer);
        return answer;
    }

    @Override
    public Film getFilmById(int id) {
        if (!films.containsKey(id)) {
            throw new GetFilmByIdException("Фильма с id: " + id + " нет");
        }
        return films.get(id);
    }
}