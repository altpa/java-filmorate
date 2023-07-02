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
        return jdbcTemplate.queryForObject("select * from films", new RowMapper<Map<Integer, Film>>() {
            @Override
            public Map<Integer, Film> mapRow(ResultSet rs, int rowNum) throws SQLException {
                Map<Integer, Film> films = null;
                do {
                    films.put(rs.getInt("film_id"), new Film(
                            rs.getInt("film_id"),
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getDate("release_date"),
                            rs.getInt("duration"),
                            rs.getInt("rate"),
                            rs.getInt("rating_id"),
                            rs.getInt("genre_list")));
                } while (rs.next());
                return films;
            }
        });
    }
    @Override
    public void addFilm(Film film) {

    }

    @Override
    public void updateFilm(int id, Film film) {

    }

    @Override
    public Film getFilmById(int id){
        return null;
    }

    @Override
    public void setLike(int id, int userId) {

    }

    @Override
    public void deleteLike(int id, int userId) {

    }
}
