package ru.yandex.practicum.filmorate.storage.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.filmException.DbFilmException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Component("filmDbStorage")
public class FilmDbStorage implements FilmStorage {
    private static final String GET_FILMS = "SELECT * FROM films";
    private static final String ADD_FILM = "INSERT INTO films(name, description, release_date, duration, rate, rating_id) VALUES(?, ?, ?, ?, ?, ?)";
    private static final String ADD_FILM_UPDATE_GENRE = "INSERT INTO film_genre_list(film_id, genre_id) VALUES(?, ?)";
    private static final String UPDATE_FILM = "UPDATE films SET name = ?, description = ?, release_date = ?, duration = ?, rate = ?, rating_id = ? WHERE film_id = ?";
    private static final String UPDATE_FILM_GENRE_DEL = "DELETE FROM film_genre_list WHERE film_id = ?";
    private static final String UPDATE_FILM_GENRE_ADD = "INSERT INTO film_genre_list(film_id, genre_id) VALUES(?, ?)";
    private static final String GET_FILM_BY_ID = "SELECT * FROM films WHERE film_id=";
    private static final String SET_LIKE = "INSERT INTO likes(film_id, user_id) VALUES(?, ?)";
    private static final String SET_LIKE_UPDATE_FILM_RATE = "UPDATE films SET rate = rate + 1 WHERE film_id = ?";
    private static final String DELETE_LIKE = "DELETE FROM likes WHERE film_id = ? AND user_id = ?";
    private static final String DELETE_LIKE_UPDATE_FILM_RATE = "UPDATE films SET rate = rate - 1 WHERE film_id = ? ";
    private static final String GET_MOST_LIKED = "SELECT * FROM films ORDER BY rate DESC LIMIT ";
    private static final String GET_MAX_ID = "SELECT max(film_id) AS maxId FROM films";
    private static final String CREATE_LIKES = "SELECT user_id FROM likes WHERE film_id = ";
    private static final String CREATE_RATING = "SELECT * FROM rating WHERE rating_id=";
    private static final String CREATE_GENRES = "SELECT fgl.genre_id, g.genre_name FROM film_genre_list fgl JOIN genre g ON g.genre_id = fgl.genre_id WHERE fgl.film_id =";
    private final JdbcTemplate jdbcTemplate;

    public FilmDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Map<Integer, Film> getFilms() {
        Map<Integer, Film> films = new HashMap<>();
        Optional<Map<Integer, Film>> filmsOpt = jdbcTemplate.query(GET_FILMS, (rs, rowNum) -> {
            do {
                films.put(rs.getInt("film_id"), createFilm(rs));
            } while (rs.next());
            return films;
        }).stream().findFirst();

        return filmsOpt.orElse(new HashMap<>());
    }

    @Override
    public void addFilm(Film film) {
        int newId = jdbcTemplate.update(ADD_FILM,
            film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(),
            film.getRate(), film.getMpa().getId());
        film.setId(newId + 1);

        if (film.getGenres() != null) {
            if (film.getGenres().size() > 0) {
                film.getGenres().forEach((genre) ->
                        jdbcTemplate.update(ADD_FILM_UPDATE_GENRE, film.getId(), genre.getId()));
            }
        }
    }

    @Override
    public void updateFilm(int id, Film film) {
        jdbcTemplate.update(UPDATE_FILM,
            film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(),
            film.getRate(), film.getMpa().getId(), id);

        if (film.getGenres() != null) {
            jdbcTemplate.update(UPDATE_FILM_GENRE_DEL, id);
            film.getGenres().forEach((genre) -> jdbcTemplate.update(UPDATE_FILM_GENRE_ADD, id, genre.getId()));
        }
    }

    @Override
    public Optional<Film> getFilmById(int id) {
            return jdbcTemplate.query(GET_FILM_BY_ID + id, (rs, rowNum) -> createFilm(rs)).stream().findFirst();
    }

    @Override
    public void setLike(int id, int userId) {
        updateLike(SET_LIKE, SET_LIKE_UPDATE_FILM_RATE, id, userId);
    }

    @Override
    public void deleteLike(int id, int userId) {
        updateLike(DELETE_LIKE, DELETE_LIKE_UPDATE_FILM_RATE, id, userId);
    }

    @Override
    public List<Film> getMostLiked(String countParam) {
        List<Film> mostLiked = new ArrayList<>();
        Optional<List<Film>> mostLikedOpt = jdbcTemplate.query(GET_MOST_LIKED + countParam, (rs, rowNum) -> {
            do {
                mostLiked.add(createFilm(rs));
            } while (rs.next());
            return mostLiked;
        }).stream().findFirst();

        return mostLikedOpt.orElse(new ArrayList<>());
    }


    @Override
    public int getMaxId() {
        Integer maxId = jdbcTemplate.queryForObject(GET_MAX_ID, (rs, rowNum) -> rs.getInt("maxId"));
        if (maxId != null) {
            return maxId;
        }
        return 0;
    }

    private Film createFilm(ResultSet rs) {
        try {
            return new Film(
                    rs.getInt("film_id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getDate("release_date").toLocalDate(),
                    rs.getInt("duration"),
                    rs.getInt("rate"),
                    createLikes(rs.getInt("film_id")),
                    createRating(rs.getInt("rating_id")),
                    createGenres(rs.getInt("film_id")));
        } catch (SQLException e) {
            throw new DbFilmException("Ошибка в БД");
        }
    }

    private Set<Integer> createLikes(int id) {
        Set<Integer> likes = new HashSet<>();
        Optional<Set<Integer>> likesOpt = jdbcTemplate.query(CREATE_LIKES + id, (rs, rowNum) -> {
                do {
                    likes.add(rs.getInt("user_id"));
                } while (rs.next());
                return likes;
            }).stream().findFirst();

        return likesOpt.orElse(new HashSet<>());
    }

    private Mpa createRating(int id) {
        return jdbcTemplate.queryForObject(CREATE_RATING + id, (rs, rowNum) ->
                new Mpa(rs.getInt("rating_id"), rs.getString("rating_name")));
    }

    private List<Genre> createGenres(int id) {
        List<Genre> genres = new ArrayList<>();
        Optional<List<Genre>> genresOpt = jdbcTemplate.query(CREATE_GENRES + id, (rs, rowNum) -> {
                do {
                    genres.add(new Genre(rs.getInt("genre_id"), rs.getString("genre_name")));
                } while (rs.next());

                HashMap<Integer, Genre> map = new HashMap<>();
                for (Genre genre : genres) {
                    int genreId = genre.getId();
                    map.putIfAbsent(genreId, genre);
                }

                List<Genre> result = new ArrayList<>(map.values());
                result.sort(Comparator.comparingInt(Genre::getId));
                return result;
            }).stream().findFirst();

        return genresOpt.orElse(new ArrayList<>());
    }

    private void updateLike(String SQL_STR_LIKE, String SQL_STR_FILM, int id, int userId) {
        jdbcTemplate.update(SQL_STR_LIKE, id, userId);
        jdbcTemplate.update(SQL_STR_FILM, id);
    }
}
