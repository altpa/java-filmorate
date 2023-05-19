package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/films")
public class FilmController {
    private final Map<Integer, Film> films = new HashMap<>();
    private int idCounter = 1;

    @PostMapping
    public Film addFilm(@RequestBody @Valid Film film) {
        log.info("Получен POST запрос для добавления фильма {}", film.getName());
        film.setId(idCounter++);
        films.put(film.getId(), film);
        log.info("Фильм {} добавлен. id={}", film.getName(), film.getId());
        return film;
    }

    @PutMapping
    public Film updateFilm(@RequestBody @Valid Film film) {
        log.info("Получен PUT запрос для обновления фильма {}, id={}", film.getName(), film.getId());
        if (films.containsKey(film.getId())) {
            log.info("Будет обновлен фильм {}", films.get(film.getId()).getName());
            films.put(film.getId(), film);
            log.info("Данные фильма обновлены");
        } else {
            log.error("Ошибка обновления фильма.");
            throw new ValidationException("Фильма с id=" + film.getId() + " нет. Невозможно обновить");
        }
        return film;
    }

    @GetMapping
    public List<Film> getFilms() {
        log.info("Получен GET запрос для списка фильмов. Всего фильмов: {}", films.size());
        return new ArrayList<>(films.values());
    }
}