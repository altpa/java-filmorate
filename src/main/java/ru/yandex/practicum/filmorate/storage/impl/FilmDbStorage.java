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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component("filmDbStorage")
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;

    public FilmDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Map<Integer, Film> getFilms() {
        final String SQL_STR = "SELECT * FROM films";
        Map<Integer, Film> films = new HashMap<>();
        try {
            return jdbcTemplate.queryForObject(SQL_STR, new RowMapper<Map<Integer, Film>>() {
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
        final String SQL_STR_FILM = "INSERT INTO films(name, description, release_date, duration, rate, rating_id) VALUES(?, ?, ?, ?, ?, ?)";
        final String SQL_STR_GENRE = "INSERT INTO film_genre_list(film_id, genre_id) VALUES(?, ?)";
        int newId = getMaxId() + 1;
        film.setId(newId);
        jdbcTemplate.update(SQL_STR_FILM,
            film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(),
            film.getRate(), film.getMpa().getId());

        if (film.getGenres() != null) {
            if (film.getGenres().size() > 0) {
                film.getGenres().forEach((genre) -> {
                    jdbcTemplate.update(SQL_STR_GENRE, film.getId(), genre.getId());
                });
            }
        }
    }

    @Override
    public void updateFilm(int id, Film film) {
        final String SQL_STR_FILM = "UPDATE films SET name = ?, description = ?, release_date = ?, duration = ?, rate = ?, rating_id = ? WHERE film_id = ?";
        final String SQL_STR_GENRE_DEL = "DELETE FROM film_genre_list WHERE film_id = ?";
        final String SQL_STR_GENRE_ADD = "INSERT INTO film_genre_list(film_id, genre_id) VALUES(?, ?)";
        jdbcTemplate.update(SQL_STR_FILM,
            film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(),
            film.getRate(), film.getMpa().getId(), id);

        if (film.getGenres() != null) {
            jdbcTemplate.update(SQL_STR_GENRE_DEL, id);
            film.getGenres().forEach((genre) -> {
                jdbcTemplate.update(SQL_STR_GENRE_ADD, id, genre.getId());
            });
        }
    }

    @Override
    public Film getFilmById(int id) {
        final String SQL_STR_FILM = "SELECT * FROM films WHERE film_id=";
        try {
            return jdbcTemplate.queryForObject(SQL_STR_FILM + id, new RowMapper<Film>() {
                @Override
                public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return createFilm(rs);
                }
            });
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void setLike(int id, int userId) {
        final String SQL_STR_LIKE_ADD = "INSERT INTO likes(film_id, user_id) VALUES(?, ?)";
        final String SQL_STR_FILM = "UPDATE films SET rate = rate + 1 WHERE film_id = ?";
        updateLike(SQL_STR_LIKE_ADD, SQL_STR_FILM, id, userId);
    }

    @Override
    public void deleteLike(int id, int userId) {
        final String SQL_STR_LIKE_DEL = "DELETE FROM likes WHERE film_id = ? AND user_id = ?";
        final String SQL_STR_FILM = "UPDATE films SET rate = rate - 1 WHERE film_id = ? ";
        updateLike(SQL_STR_LIKE_DEL, SQL_STR_FILM, id, userId);
    }

    @Override
    public List<Film> getMostLiked(String countParam) {
        final String SQL_STR = "SELECT * FROM films ORDER BY rate DESC LIMIT ";
        List<Film> mostLiked = new ArrayList<>();
        try {
            return jdbcTemplate.queryForObject(SQL_STR + countParam, new RowMapper<List<Film>>() {
                @Override
                public List<Film> mapRow(ResultSet rs, int rowNum) throws SQLException {
                    do {
                        mostLiked.add(createFilm(rs));
                    } while (rs.next());
                    return mostLiked;
                }
            });
        } catch (EmptyResultDataAccessException e) {
            return mostLiked;
        }
    }

    @Override
    public int getMaxId() {
        final String SQL_STR = "SELECT max(film_id) AS maxId FROM films";
        Integer maxId = jdbcTemplate.queryForObject(SQL_STR, new RowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getInt("maxId");
            }
        });
        if (maxId != null) {
            return maxId;
        }
        return 0;
    }

    private Film createFilm(ResultSet rs) throws SQLException {
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
    }

    private Set<Integer> createLikes(int id) {
        final String SQL_STR = "SELECT user_id FROM likes WHERE film_id = ";
        Set<Integer> likes = new HashSet<>();
        try {
            return jdbcTemplate.queryForObject(SQL_STR + id, new RowMapper<Set<Integer>>() {
                @Override
                public Set<Integer> mapRow(ResultSet rs, int rowNum) throws SQLException {
                    do {
                        likes.add(rs.getInt("user_id"));
                    } while (rs.next());
                    return likes;
                }
            });
        } catch (EmptyResultDataAccessException e) {
            return likes;
        }
    }

    private Mpa createRating(int id) {
        final String SQL_STR = "SELECT * FROM rating WHERE rating_id=";
        return jdbcTemplate.queryForObject(SQL_STR + id, new RowMapper<Mpa>() {
            @Override
            public Mpa mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Mpa(rs.getInt("rating_id"), rs.getString("rating_name"));
            }
        });
    }

    private List<Genre> createGenres(int id) {
        final String SQL_STR = "SELECT fgl.genre_id, g.genre_name FROM film_genre_list fgl JOIN genre g ON g.genre_id = fgl.genre_id WHERE fgl.film_id =";
        List<Genre> genres = new ArrayList<>();
        if (id > 0) {
            try {
                return jdbcTemplate.queryForObject(SQL_STR + id, new RowMapper<List<Genre>>() {
                    @Override
                    public List<Genre> mapRow(ResultSet rs, int rowNum) throws SQLException {
                        do {
                            genres.add(new Genre(rs.getInt("genre_id"), rs.getString("genre_name")));
                        } while (rs.next());

                        HashMap<Integer, Genre> map = new HashMap<Integer, Genre>();
                        for (Genre genre : genres) {
                            int genreId = genre.getId();
                            map.putIfAbsent(genreId, genre);
                        }

                        List<Genre> result = new ArrayList<Genre>(map.values());
                        result.sort(Comparator.comparingInt(Genre::getId));
                        return result;
                    }
                });
            } catch (EmptyResultDataAccessException e) {
                return genres;
            }
        }
        return genres;
    }

    private void updateLike(String SQL_STR_LIKE, String SQL_STR_FILM, int id, int userId) {
        jdbcTemplate.update(SQL_STR_LIKE, id, userId);
        jdbcTemplate.update(SQL_STR_FILM, id);
    }
}
