package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.impl.InMemoryFilmStorage;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/films")
public class FilmController {
    private final InMemoryFilmStorage inMemoryFilmStorage = new InMemoryFilmStorage();

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        return inMemoryFilmStorage.addFilm(film);
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        return inMemoryFilmStorage.updateFilm(film);
    }

    @GetMapping
    public List<Film> getFilms() {
        return inMemoryFilmStorage.getFilms();
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable int id) {
        return inMemoryFilmStorage.getFilmById(id);
    }

    @PutMapping("/{id}/like/{userId}")
    public Film setLike(@PathVariable int id, @PathVariable int userId) {
        return inMemoryFilmStorage.setLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public Film deleteLike(@PathVariable int id, @PathVariable int userId) {
        return inMemoryFilmStorage.deleteLike(id, userId);
    }

    @GetMapping("/popular")
    public List<Film> getMostLiked(@RequestParam(required = false, defaultValue = "10") String count) {
        return inMemoryFilmStorage.getMostLiked(count);
    }
}