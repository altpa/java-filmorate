package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Map;

public interface FilmStorage {
    Map<Integer, Film> getFilms();

    void addFilm(Film film);

    void updateFilm(int id, Film film);

    Film getFilmById(int id);

    void setLike(int id, int userId);

    void deleteLike(int id, int userId);

    List<Film> getMostLiked(String countParam);

    int getMaxId();
}
