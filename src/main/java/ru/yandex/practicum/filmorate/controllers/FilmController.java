package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    public Film addFilm(@Valid @RequestBody Film film) {
        log.info("Получен POST запрос для добавления фильма: {}", film);
        film.setId(idCounter++);
        films.put(film.getId(), film);
        log.info("Фильм добавлен: {}", film);
        return film;
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        log.info("Получен PUT запрос для обновления фильма: {}", film);
        if (films.containsKey(film.getId())) {
            log.info("Будет обновлен фильм {}", films.get(film.getId()));
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
        val allFilms = new ArrayList<>(films.values());
        log.info("Получен GET запрос для списка фильмов: {}", allFilms);
        return allFilms;
    }
}