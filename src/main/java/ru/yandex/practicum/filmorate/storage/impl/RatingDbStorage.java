package ru.yandex.practicum.filmorate.storage.impl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.RatingStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class RatingDbStorage implements RatingStorage {
    private final JdbcTemplate jdbcTemplate;

    public RatingDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Mpa> getRatings() {
        final String SQL_STR = "SELECT * FROM rating";
        List<Mpa> ratings = new ArrayList<>();
        try {
            return jdbcTemplate.queryForObject(SQL_STR, new RowMapper<List<Mpa>>() {
                @Override
                public List<Mpa> mapRow(ResultSet rs, int rowNum) throws SQLException {
                    do {
                        ratings.add(createRating(rs));
                    } while (rs.next());
                    return ratings;
                }
            });
        } catch (EmptyResultDataAccessException e) {
            return ratings;
        }
    }

    @Override
    public Mpa getRatingById(int id) {
        final String SQL_STR = "select * from rating where rating_id = ";
        try {
            return jdbcTemplate.queryForObject(SQL_STR + id, new RowMapper<Mpa>() {
                @Override
                public Mpa mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return createRating(rs);
                }
            });
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Integer> getMpaIds() {
        final String SQL_STR = "SELECT rating_id FROM rating";
        List<Integer> mpaIds = new ArrayList<>();
        try {
            return jdbcTemplate.queryForObject(SQL_STR, new RowMapper<List<Integer>>() {
                @Override
                public List<Integer> mapRow(ResultSet rs, int rowNum) throws SQLException {
                    do {
                        mpaIds.add(rs.getInt("rating_id"));
                    } while (rs.next());
                    return mpaIds;
                }
            });
        } catch (EmptyResultDataAccessException e) {
            return mpaIds;
        }
    }

    private Mpa createRating(ResultSet rs) throws SQLException {
        return new Mpa(rs.getInt("rating_id"), rs.getString("rating_name"));
    }
}
