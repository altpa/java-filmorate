package ru.yandex.practicum.filmorate.controllers.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.controllers.Controller;
import ru.yandex.practicum.filmorate.model.impl.Film;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping("/films")
public class FilmController extends Controller {
    @RequestMapping(method={RequestMethod.POST, RequestMethod.PUT})
    public Film create(@RequestBody @Valid Film film) {
        add(film);
        return film;
    }
}
