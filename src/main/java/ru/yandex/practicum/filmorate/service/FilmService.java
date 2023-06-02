package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.filmException.AddFilmException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Map;

@Slf4j
@Service
public class FilmService {
    public Film checkFilm(Map<Integer, Film> films, Film film) {
        if (films.containsKey(film.getId())) {
            log.info("Будет обновлен фильм {}", films.get(film.getId()));
            films.put(film.getId(), film);
            log.info("Данные фильма обновлены");
        } else {
            log.error("Ошибка обновления фильма.");
            throw new AddFilmException("Фильма с id=" + film.getId() + " нет. Невозможно обновить");
        }
        return film;
    }
}
