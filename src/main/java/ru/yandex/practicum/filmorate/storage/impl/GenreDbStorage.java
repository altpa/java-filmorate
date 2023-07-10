package ru.yandex.practicum.filmorate.storage.impl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class GenreDbStorage implements GenreStorage {
    private final JdbcTemplate jdbcTemplate;

    public GenreDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Genre> getGenres() {
        final String SQL_STR = "SELECT * FROM genre";
        List<Genre> genres = new ArrayList<>();
        try {
            return jdbcTemplate.queryForObject(SQL_STR, new RowMapper<List<Genre>>() {
                final List<Genre> genres = new ArrayList<>();
                @Override
                public List<Genre> mapRow(ResultSet rs, int rowNum) throws SQLException {
                    do {
                        genres.add(createGenre(rs));
                    } while (rs.next());
                    genres.sort((o1, o2) -> o1.getId() - (o2.getId()));
                    return genres;
                }
            });
        } catch (EmptyResultDataAccessException e) {
            return genres;
        }
    }

    @Override
    public Genre getGenreById(int id) {
        final String SQL_STR = "select * from genre where genre_id = ";
        try {
            return jdbcTemplate.queryForObject(SQL_STR + id, new RowMapper<Genre>() {
                @Override
                public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return createGenre(rs);
                }
            });
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Integer> getGenreIds() {
        final String SQL_STR = "SELECT genre_id FROM genre";
        List<Integer> genres = new ArrayList<>();
        try {
            return jdbcTemplate.queryForObject(SQL_STR, new RowMapper<List<Integer>>() {
                @Override
                public List<Integer> mapRow(ResultSet rs, int rowNum) throws SQLException {
                    do {
                        genres.add(rs.getInt("genre_id"));
                    } while (rs.next());
                    return genres;
                }
            });
        } catch (EmptyResultDataAccessException e) {
            return genres;
        }
    }

    private Genre createGenre(ResultSet rs) throws SQLException {
        return new Genre(rs.getInt("genre_id"), rs.getString("genre_name"));
    }
}
