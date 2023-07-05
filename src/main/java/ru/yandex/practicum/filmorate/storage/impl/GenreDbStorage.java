package ru.yandex.practicum.filmorate.storage.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class GenreDbStorage implements GenreStorage {
    private final JdbcTemplate jdbcTemplate;

    public GenreDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Genre> getGenres() {
        return jdbcTemplate.queryForObject("SELECT * FROM genre", new RowMapper<List<Genre>>() {
            List<Genre> genres = new ArrayList<>();
            @Override
            public List<Genre> mapRow(ResultSet rs, int rowNum) throws SQLException {
                do {
                    genres.add(createGenre(rs));
                } while (rs.next());
                return genres;
            }
        });
    }

    @Override
    public Genre getGenreById(int id) {
        return jdbcTemplate.queryForObject("select * from ratings where genre_id = " + id, new RowMapper<Genre>() {
            @Override
            public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
                return createGenre(rs);
            }
        });
    }

    private Genre createGenre(ResultSet rs) throws SQLException {
        return new Genre(rs.getInt("genre_id"), rs.getString("genre_name"));
    }
}
