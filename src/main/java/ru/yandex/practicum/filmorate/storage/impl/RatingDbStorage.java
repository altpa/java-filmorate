package ru.yandex.practicum.filmorate.storage.impl;

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
        return jdbcTemplate.queryForObject("SELECT * FROM ratings", new RowMapper<List<Mpa>>() {
            List<Mpa> ratings = new ArrayList<>();
            @Override
            public List<Mpa> mapRow(ResultSet rs, int rowNum) throws SQLException {
                do {
                    ratings.add(createRating(rs));
                } while (rs.next());
                return ratings;
            }
        });
    }

    @Override
    public Mpa getRatingById(int id) {
        return jdbcTemplate.queryForObject("select * from ratings where rating_id = " + id, new RowMapper<Mpa>() {
            @Override
            public Mpa mapRow(ResultSet rs, int rowNum) throws SQLException {
                return createRating(rs);
            }
        });
    }

    private Mpa createRating(ResultSet rs) throws SQLException {
        return new Mpa(rs.getInt("rating_id"), rs.getString("rating_name"));
    }
}
