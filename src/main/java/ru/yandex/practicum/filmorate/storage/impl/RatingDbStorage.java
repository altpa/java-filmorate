package ru.yandex.practicum.filmorate.storage.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.mpaExceptions.DbMpaException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.RatingStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class RatingDbStorage implements RatingStorage {
    private final JdbcTemplate jdbcTemplate;

    public RatingDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String GET_RATINGS = "SELECT * FROM rating";
    private static final String GET_RATING_BY_ID = "select * from rating where rating_id = ";
    private static final String GET_MPA_IDS = "SELECT rating_id FROM rating";

    @Override
    public List<Mpa> getRatings() {
        List<Mpa> ratings = new ArrayList<>();
        Optional<List<Mpa>> ratingOpt = jdbcTemplate.query(GET_RATINGS, (rs, rowNum) -> {
            do {
                ratings.add(createRating(rs));
            } while (rs.next());
            return ratings;
        }).stream().findFirst();

        return ratingOpt.orElse(new ArrayList<>());
    }


    @Override
    public Mpa getRatingById(int id) {
        return jdbcTemplate.queryForObject(GET_RATING_BY_ID + id, (rs, rowNum) -> createRating(rs));
    }

    @Override
    public List<Integer> getMpaIds() {
        List<Integer> mpaIds = new ArrayList<>();
        Optional<List<Integer>> mpaIdsOpt = jdbcTemplate.query(GET_MPA_IDS, (rs, rowNum) -> {
            do {
                mpaIds.add(rs.getInt("rating_id"));
            } while (rs.next());
            return mpaIds;
        }).stream().findFirst();

        return mpaIdsOpt.orElse(new ArrayList<>());
    }

    private Mpa createRating(ResultSet rs) {
        try {
            return new Mpa(rs.getInt("rating_id"), rs.getString("rating_name"));
        } catch (SQLException e) {
            throw new DbMpaException("Ошибка в БД");
        }
    }
}
