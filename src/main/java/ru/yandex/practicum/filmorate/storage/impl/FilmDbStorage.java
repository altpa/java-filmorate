package ru.yandex.practicum.filmorate.storage.impl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component("filmDbStorage")
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;

    public FilmDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Map<Integer, Film> getFilms() {
        Map<Integer, Film> films = new HashMap<>();
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM films", new RowMapper<Map<Integer, Film>>() {
                @Override
                public Map<Integer, Film> mapRow(ResultSet rs, int rowNum) throws SQLException {
                    do {
                        films.put(rs.getInt("film_id"), createFilm(rs));
                    } while (rs.next());
                    return films;
                }
            });
        } catch (EmptyResultDataAccessException e) {
            return films;
        }
    }

    @Override
    public void addFilm(Film film) {
        jdbcTemplate.update(
            "INSERT INTO films(name, description, release_date, duration, rate, rating_id) VALUES(?, ?, ?, ?, ?, ?)",
            film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(),
            film.getRate(), film.getMpa().getId());

        if (film.getGenres() != null) {
            if (film.getGenres().size() > 0) {
                film.getGenres().forEach((genre) -> {
                    jdbcTemplate.update("INSERT INTO film_genre_list(film_id, genre_id) VALUES(?, ?)", film.getId(), genre.getId());
                });
            }
        }
    }

    @Override
    public void updateFilm(int id, Film film) {
        jdbcTemplate.update(
            "UPDATE films SET name = ?, description = ?, release_date = ?, duration = ?, rate = ?, rating_id = ? WHERE film_id = ?",
            film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(),
            film.getRate(), film.getMpa().getId(), id);

        if (film.getGenres() != null) {
            if (film.getGenres().size() > 0) {
                film.getGenres().forEach((genre) -> {
                    jdbcTemplate.update("DELETE FROM film_genre_list WHERE film_id = ?", id);
                    jdbcTemplate.update("INSERT INTO film_genre_list(film_id, genre_id) VALUES(?, ?)", id, genre.getId());
                });
            }
        }
    }

    @Override
    public Film getFilmById(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM films WHERE film_id=" + id, new RowMapper<Film>() {
            @Override
            public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
                return createFilm(rs);
            }
        });
    }

    @Override
    public void setLike(int id, int userId) {
        jdbcTemplate.update("INSERT INTO likes(film_id, user_id) VALUES(?, ?)", id, userId);
    }

    @Override
    public void deleteLike(int id, int userId) {
        jdbcTemplate.update("DELETE FROM likes WHERE film_id = ? AND user_id = ?", id, userId);
    }

    @Override
    public List<Film> getMostLiked(String countParam) {
        return null;
    }

    private Film createFilm(ResultSet rs) throws SQLException {
        return new Film(
                rs.getInt("film_id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getDate("release_date").toLocalDate(),
                rs.getInt("duration"),
                rs.getInt("rate"),
                createRating(rs.getInt("rating_id")),
                createGenres(rs.getInt("film_id")));
    }

    private Mpa createRating(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM rating WHERE rating_id=" + id, new RowMapper<Mpa>() {
            @Override
            public Mpa mapRow(ResultSet rs, int rowNum) throws SQLException {
                Mpa mpa = new Mpa(rs.getInt("rating_id"), rs.getString("rating_name"));
                return new Mpa(rs.getInt("rating_id"), rs.getString("rating_name"));
            }
        });
    }

    private Set<Genre> createGenres(int id) {
        Set<Genre> genres = new HashSet<>();
        if (id > 0) {
            try {
                return jdbcTemplate.queryForObject("SELECT fgl.genre_id, g.genre_name FROM film_genre_list fgl JOIN genre g ON g.genre_id = fgl.genre_id WHERE fgl.film_id =" + id, new RowMapper<Set<Genre>>() {
                    @Override
                    public Set<Genre> mapRow(ResultSet rs, int rowNum) throws SQLException {
                        do {
                            genres.add(new Genre(rs.getInt("genre_id"), rs.getString("genre_name")));
                        } while (rs.next());
                        return genres;
                    }
                });
            } catch (EmptyResultDataAccessException e) {
                return genres;
            }
        }
        return genres;
    }
}
