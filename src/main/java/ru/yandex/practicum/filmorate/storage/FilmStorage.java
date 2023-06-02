package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.List;

@Component
public interface FilmStorage {

    Film addFilm(@Valid @RequestBody Film film);

    Film updateFilm(@Valid @RequestBody Film film);

    List<Film> getFilms();

    Film getFilmById(int id);

    Film setLike(int id, int userId);

    Film deleteLike(int id, int userId);

    List<Film> getMostLiked(String count);
}
