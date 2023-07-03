package ru.yandex.practicum.filmorate.storage.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

@Component("filmDbStorage")
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;

    public FilmDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Map<Integer, Film> getFilms() {
        return jdbcTemplate.queryForObject("SELECT * FROM films", new RowMapper<Map<Integer, Film>>() {
            @Override
            public Map<Integer, Film> mapRow(ResultSet rs, int rowNum) throws SQLException {
                Map<Integer, Film> films = null;
                do {
                    films.put(rs.getInt("film_id"), createFilm(rs));
                } while (rs.next());
                return films;
            }
        });
    }

    @Override
    public void addFilm(Film film) {
        jdbcTemplate.update(
            "INSERT INTO films(name, description, release_date, duration, rate, rating_id, genre_list) VALUES(?, ?, ?, ?, ?, ?, ?)",
            film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(),
            film.getRate(), film.getRating(), film.getGenre());
    }

    @Override
    public void updateFilm(int id, Film film) {
        jdbcTemplate.update(
            "UPDATE films SET name = ?, description = ?, release_date = ?, duration = ?, rate = ?, rating_id = ?, genre_list = ? WHERE film_id = ?",
            film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(),
            film.getRate(), film.getRating(), film.getGenre(), id);
    }

    @Override
    public Film getFilmById(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM films WHERE film_id = " + id, new RowMapper<Film>() {
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

    private Film createFilm(ResultSet rs) throws SQLException {
        return new Film(
                rs.getInt("film_id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getDate("release_date"),
                rs.getInt("duration"),
                rs.getInt("rate"),
                rs.getInt("rating_id"),
                rs.getInt("genre_list"));
    }
}
