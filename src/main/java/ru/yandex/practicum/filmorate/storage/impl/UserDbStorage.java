package ru.yandex.practicum.filmorate.storage.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component("userDbStorage")
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;

    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Map<Integer, User> getUsers() {
        Map<Integer, User> users = new HashMap<>();
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM users", new RowMapper<Map<Integer, User>>() {
                @Override
                public Map<Integer, User> mapRow(ResultSet rs, int rowNum) throws SQLException {
                    do {
                        users.put(rs.getInt("user_id"), createUser(rs));
                    } while (rs.next());
                    return users;
                }
            });
        } catch (EmptyResultDataAccessException e) {
            return users;
        }
    }

    @Override
    public void addUser(User user) {
        int newId = getMaxId() + 1;
        user.setId(newId);
        jdbcTemplate.update("INSERT INTO users(name, email, login, birthday) VALUES(?, ?, ?, ?)",
                user.getName(), user.getEmail(), user.getLogin(), user.getBirthday());
    }

    @Override
    public void updateUser(User user) {
        jdbcTemplate.update("UPDATE users SET name = ?, email = ?, login = ?, birthday = ? WHERE user_id = ?",
                user.getName(), user.getEmail(), user.getLogin(), user.getBirthday(), user.getId());
    }

    @Override
    public void addToFriends(int id, int friendId) {
        jdbcTemplate.update("INSERT INTO friends(user_id, friend_id) VALUES (?, ?)", id, friendId);
    }

    @Override
    public void deleteFromFriends(int id, int friendId) {
        jdbcTemplate.update("DELETE FROM friends WHERE user_id = ? AND friend_id = ?", id, friendId);
    }

    @Override
    public List<User> getFriends(int id) {
        List<User> friends = new ArrayList<>();
        try {
            jdbcTemplate.queryForObject("select * from users u where u.user_id in (select friend_id from friends where user_id = " + id + " union select user_id from friends where friend_id = " + id + "  and user_id = " + id + ")", new RowMapper<List<User>>() {
                @Override
                public List<User> mapRow(ResultSet rs, int rowNum) throws SQLException {
                    do {
                        friends.add(createUser(rs));
                    } while (rs.next());
                    return friends;
                }
            });
            return friends;
        } catch (EmptyResultDataAccessException e) {
            return friends;
        }
    }

    @Override
    public List<User> getCommonFriends(int id, int friendId) {
        List<User> commonFriends = new ArrayList<>();
        try {
            jdbcTemplate.queryForObject("select * from users u where u.user_id in (select friend_id from friends f where (f.user_id = " + id + " or f.user_id = " + friendId + ") and not (friend_id = " + id + " or friend_id = " + friendId + ") group by friend_id);", new RowMapper<List<User>>() {
                @Override
                public List<User> mapRow(ResultSet rs, int rowNum) throws SQLException {
                    do {
                        commonFriends.add(createUser(rs));
                    } while (rs.next());
                    return commonFriends;
                }
            });
            return commonFriends;
        } catch (EmptyResultDataAccessException e) {
            return commonFriends;
        }

    }

    @Override
    public User getUserById(int id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM users WHERE user_id = " + id, new RowMapper<User>() {
                @Override
                public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return createUser(rs);
                }
            });
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public int getMaxId() {
        Integer maxId = jdbcTemplate.queryForObject(
                "SELECT max(user_id) as maxId FROM users;", new RowMapper<Integer>() {
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

    private User createUser(ResultSet rs) throws SQLException {
        return new User(
                rs.getInt("user_id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("login"),
                rs.getDate("birthday").toLocalDate());
    }
}
