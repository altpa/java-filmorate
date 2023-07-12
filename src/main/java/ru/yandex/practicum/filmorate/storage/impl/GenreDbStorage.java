package ru.yandex.practicum.filmorate.storage.impl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.genreExceptions.DbGenreException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Component
public class GenreDbStorage implements GenreStorage {
    private static final String GET_GENRES = "SELECT * FROM genre";
    private static final String GET_GENRE_BY_ID = "select * from genre where genre_id = ";
    private static final String GET_GENRE_IDS = "SELECT genre_id FROM genre";
    private final JdbcTemplate jdbcTemplate;

    public GenreDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Genre> getGenres() {
        List<Genre> genres = new ArrayList<>();
        Optional<List<Genre>> genresOpt = jdbcTemplate.query(GET_GENRES, (rs, rowNum) -> {
            do {
                genres.add(createGenre(rs));
            } while (rs.next());
            genres.sort(Comparator.comparingInt(Genre::getId));
            return genres;
        }).stream().findFirst();

        return genresOpt.orElse(new ArrayList<>());
    }

    @Override
    public Genre getGenreById(int id) {
        try {
            return jdbcTemplate.queryForObject(GET_GENRE_BY_ID + id, (rs, rowNum) -> createGenre(rs));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Integer> getGenreIds() {
        List<Integer> genres = new ArrayList<>();
        Optional<List<Integer>> genresOpt = jdbcTemplate.query(GET_GENRE_IDS, (rs, rowNum) -> {
            do {
                genres.add(rs.getInt("genre_id"));
            } while (rs.next());
            return genres;
        }).stream().findFirst();

        return genresOpt.orElse(new ArrayList<>());
    }

    private Genre createGenre(ResultSet rs) {
        try {
            return new Genre(rs.getInt("genre_id"), rs.getString("genre_name"));
        } catch (SQLException e) {
            throw new DbGenreException("Ошибка в БД");
        }
    }
}
