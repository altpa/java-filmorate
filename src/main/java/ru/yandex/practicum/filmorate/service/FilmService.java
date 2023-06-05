package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.filmException.BadRequestFilmException;
import ru.yandex.practicum.filmorate.exceptions.filmException.InternalServerErrorFilmException;
import ru.yandex.practicum.filmorate.exceptions.filmException.NotFoundFilmException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.validation.Validation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmStorage filmStorage;
    private int idCounter = 1;

    public Film addFilm(Film film) {
        if (film == null) {
            throw new BadRequestFilmException("Пустой запрос");
        }
        log.info("Получен POST запрос для добавления фильма: {}", film);
        film.setId(idCounter++);
        filmStorage.addFilm(film.getId(), film);
        log.info("Фильм добавлен: {}", film);
        return film;
    }

    public Film updateFilm(Film film) {
        if (film == null) {
            throw new InternalServerErrorFilmException("Пустой запрос");
        }
        if (!filmStorage.getFilms().containsKey(film.getId())) {
            throw new InternalServerErrorFilmException("Такого фильма нет id: " + film.getId() + ", нечего обновлять");
        }
        log.info("Получен PUT запрос для обновления фильма: {}", film);
        return Validation.checkFilm(filmStorage.getFilms(), film);
    }

    public List<Film> getFilms() {
        log.info("Получен GET запрос для списка фильмов");
        val answer = new ArrayList<>(filmStorage.getFilms().values());
        log.info("Отправлен список фильмов: {}", answer);
        return answer;
    }

    public Film setLike(int id, int userId) {
        if (!filmStorage.getFilms().containsKey(id)) {
            throw new BadRequestFilmException("Фильма с id: " + id + " нет");
        }
        log.info("Получен PUT запрос для добавления лайка фильму {}", filmStorage.getFilms().get(id));
        filmStorage.getFilms().get(id).getLikes().add(userId);
        log.info("Фильму {} добавлен лайк", filmStorage.getFilms().get(id));
        return filmStorage.getFilms().get(id);
    }

    public Film deleteLike(int id, int userId) {
        if (!filmStorage.getFilms().containsKey(id)) {
            throw new NotFoundFilmException("Фильма с id: " + id + " нет");
        }
        if (!filmStorage.getFilms().get(id).getLikes().contains(userId)) {
            throw new NotFoundFilmException("У фильма нет лайка пользователя id:" + userId);
        }
        log.info("Получен DELETE запрос для удаления лайка у фильма {}", filmStorage.getFilms().get(id));
        filmStorage.getFilms().get(id).getLikes().remove(userId);
        log.info("Фильму {} удален лайк", filmStorage.getFilms().get(id));
        return filmStorage.getFilms().get(id);
    }

    public List<Film> getMostLiked(String countParam) {
        int count = Integer.parseInt(countParam);
        log.info("Получен GET запрос для получения списка первых {} самых залайканых фильмов", count);
        if (count < 0) {
            throw new BadRequestFilmException("count отрицательное");
        }
        List<Film> answer = filmStorage.getFilms().values().stream()
                .sorted((film1, film2) -> film2.getLikes().size() - film1.getLikes().size())
                .limit(count)
                .collect(Collectors.toList());
        log.info("Отправлен список фильмов: {}", answer);
        return answer;
    }

    public Film getFilmById(int id) {
        if (!filmStorage.getFilms().containsKey(id)) {
            throw new NotFoundFilmException("Фильма с id: " + id + " нет");
        }
        return filmStorage.getFilms().get(id);
    }
}
