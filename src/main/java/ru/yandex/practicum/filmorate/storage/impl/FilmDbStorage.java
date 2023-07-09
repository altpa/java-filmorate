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
        Map<Integer, Film> films = new HashMap<>();
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM films", new RowMapper<Map<Integer, Film>>() {
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
        int newId = getMaxId() + 1;
        film.setId(newId);
        jdbcTemplate.update(
            "INSERT INTO films(name, description, release_date, duration, rate, rating_id) VALUES(?, ?, ?, ?, ?, ?)",
            film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(),
            film.getRate(), film.getMpa().getId());

        if (film.getGenres() != null) {
            if (film.getGenres().size() > 0) {
                film.getGenres().forEach((genre) -> {
                    jdbcTemplate.update("INSERT INTO film_genre_list(film_id, genre_id) VALUES(?, ?)", film.getId(), genre.getId());
                });
            }
        }
    }

    @Override
    public void updateFilm(int id, Film film) {
        jdbcTemplate.update(
            "UPDATE films SET name = ?, description = ?, release_date = ?, duration = ?, rate = ?, rating_id = ? WHERE film_id = ?",
            film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(),
            film.getRate(), film.getMpa().getId(), id);

        if (film.getGenres() != null) {
            jdbcTemplate.update("DELETE FROM film_genre_list WHERE film_id = ?", id);
            film.getGenres().forEach((genre) -> {
                jdbcTemplate.update("INSERT INTO film_genre_list(film_id, genre_id) VALUES(?, ?)", id, genre.getId());
            });
        }
    }

    @Override
    public Film getFilmById(int id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM films WHERE film_id=" + id, new RowMapper<Film>() {
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
        jdbcTemplate.update("INSERT INTO likes(film_id, user_id) VALUES(?, ?)", id, userId);
        jdbcTemplate.update("update films SET rate = rate + 1 where film_id = ? ", id);
    }

    @Override
    public void deleteLike(int id, int userId) {
        jdbcTemplate.update("DELETE FROM likes WHERE film_id = ? AND user_id = ?", id, userId);
        jdbcTemplate.update("UPDATE films SET rate = rate - 1 WHERE film_id = ? ", id);
    }

    @Override
    public List<Film> getMostLiked(String countParam) {
        List<Film> mostLiked = new ArrayList<>();
        try {
          jdbcTemplate.queryForObject("SELECT * FROM films ORDER BY rate DESC LIMIT " + countParam + ";", new RowMapper<List<Film>>() {
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
        return mostLiked;
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
        Set<Integer> likes = new HashSet<>();
        try {
            return jdbcTemplate.queryForObject("select user_id from likes where film_id = " + id, new RowMapper<Set<Integer>>() {
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
        return jdbcTemplate.queryForObject("SELECT * FROM rating WHERE rating_id=" + id, new RowMapper<Mpa>() {
            @Override
            public Mpa mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Mpa(rs.getInt("rating_id"), rs.getString("rating_name"));
            }
        });
    }

    private List<Genre> createGenres(int id) {
        List<Genre> genres = new ArrayList<>();
        if (id > 0) {
            try {
                    return jdbcTemplate.queryForObject("SELECT fgl.genre_id, g.genre_name FROM film_genre_list fgl JOIN genre g ON g.genre_id = fgl.genre_id WHERE fgl.film_id =" + id, new RowMapper<List<Genre>>() {
                    @Override
                    public List<Genre> mapRow(ResultSet rs, int rowNum) throws SQLException {
                        do {
                            genres.add(new Genre(rs.getInt("genre_id"), rs.getString("genre_name")));
                        } while (rs.next());

                        HashMap<Integer, Genre> map = new HashMap<Integer, Genre>();
                        for (Genre genre : genres) {
                            int genreId = genre.getId();
                            if (!map.containsKey(genreId)) {
                                map.put(genreId, genre);
                            }
                        }

                        List<Genre> result = new ArrayList<Genre>(map.values());
                        result.sort((o1, o2) -> o1.getId() - (o2.getId()));
                        return result;
                    }
                });
            } catch (EmptyResultDataAccessException e) {
                return genres;
            }
        }
        return genres;
    }

    @Override
    public int getMaxId() {
        Integer maxId = jdbcTemplate.queryForObject(
                "SELECT max(film_id) as maxId FROM films;", new RowMapper<Integer>() {
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
}
