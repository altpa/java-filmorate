package ru.yandex.practicum.filmorate.storage;

import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.Map;

public interface FilmStorage {
    void addFilm(int id, @Valid @RequestBody Film film);

    Map<Integer, Film> getFilms();
}
