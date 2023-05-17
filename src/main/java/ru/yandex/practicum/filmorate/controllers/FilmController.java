package ru.yandex.practicum.filmorate.controllers;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/films")
public class FilmController {
    private final Map<Integer, Film> films = new HashMap<>();

//    public FilmController() {
//        super();
//    }
//
//    @PostMapping
//    @Override
//    public Film create(@RequestBody Film film) {
//        super.create(film);
//        return film;
//    }

    @PostMapping
    public Film create(@RequestBody Film film) {
        films.put(film.getId(), film);
        return film;
    }

    @PutMapping
    public Film update(@RequestBody Film film) {
        films.put(film.getId(), film);
        return film;
    }

    @GetMapping
    public Map<Integer, Film> get(){
        return films;
    }





//    добавление фильма; POST
//    обновление фильма; PUT
//    получение всех фильмов. GET

//    название не может быть пустым;
//    максимальная длина описания — 200 символов;
//    дата релиза — не раньше 28 декабря 1895 года;
//    продолжительность фильма должна быть положительной.
}
