package ru.yandex.practicum.filmorate.storage.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component("userDbStorage")
public class UserDbStorage implements UserStorage {
    private static final String GET_USERS = "SELECT * FROM users";
    private static final String ADD_USER = "INSERT INTO users(name, email, login, birthday) VALUES(?, ?, ?, ?)";
    private static final String UPDATE_USER = "UPDATE users SET name = ?, email = ?, login = ?, birthday = ? WHERE user_id = ?";
    private static final String ADD_TO_FRIENDS = "INSERT INTO friends(user_id, friend_id) VALUES (?, ?)";
    private static final String DELETE_FROM_FRIENDS = "DELETE FROM friends WHERE user_id = ? AND friend_id = ?";
    private static final String GET_USER_BY_ID = "SELECT * FROM users WHERE user_id = ";
    private static final String GET_MAX_ID = "SELECT max(user_id) as maxId FROM users;";
    private final JdbcTemplate jdbcTemplate;

    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Map<Integer, User> getUsers() {
        Map<Integer, User> users = new HashMap<>();
        Optional<Map<Integer, User>> usersOpt = jdbcTemplate.query(GET_USERS, (rs, rowNum) -> {
            do {
                users.put(rs.getInt("user_id"), createUser(rs));
            } while (rs.next());
            return users;
        }).stream().findFirst();

        return usersOpt.orElse(new HashMap<>());
    }

    @Override
    public void addUser(User user) {
        jdbcTemplate.update(ADD_USER,
                user.getName(), user.getEmail(), user.getLogin(), user.getBirthday());
    }

    @Override
    public void updateUser(User user) {
        jdbcTemplate.update(UPDATE_USER,
                user.getName(), user.getEmail(), user.getLogin(), user.getBirthday(), user.getId());
    }

    @Override
    public void addToFriends(int id, int friendId) {
        jdbcTemplate.update(ADD_TO_FRIENDS, id, friendId);
    }

    @Override
    public void deleteFromFriends(int id, int friendId) {
        jdbcTemplate.update(DELETE_FROM_FRIENDS, id, friendId);
    }

    @Override
    public List<User> getFriends(int id) {
        final String GET_FRIENDS = "select * from users u where u.user_id in (select friend_id from friends where user_id = " + id + " union select user_id from friends where friend_id = " + id + "  and user_id = " + id + ")";
        return getFriends(GET_FRIENDS);
    }

    @Override
    public List<User> getCommonFriends(int id, int friendId) {
        final String GET_COMMON_FRIENDS = "select * from users u where u.user_id in (select friend_id from friends f where (f.user_id = " + id + " or f.user_id = " + friendId + ") and not (friend_id = " + id + " or friend_id = " + friendId + ") group by friend_id);";
        return getFriends(GET_COMMON_FRIENDS);
    }

    @Override
    public Optional<User> getUserById(int id) {
        return jdbcTemplate.query(GET_USER_BY_ID + id, (rs, rowNum) -> createUser(rs)).stream().findFirst();
    }

    @Override
    public int getMaxId() {
        Integer maxId = jdbcTemplate.queryForObject(GET_MAX_ID, (rs, rowNum) -> rs.getInt("maxId"));
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

    private List<User> getFriends(String SQL_STR) {
        List<User> friends = new ArrayList<>();
        Optional<List<User>> friendsOpt = jdbcTemplate.query(SQL_STR, (rs, rowNum) -> {
            do {
                friends.add(createUser(rs));
            } while (rs.next());
            return friends;
        }).stream().findFirst();

        return friendsOpt.orElse(new ArrayList<>());
    }

}
