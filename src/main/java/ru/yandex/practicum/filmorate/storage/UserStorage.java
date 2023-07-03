package ru.yandex.practicum.filmorate.storage;

import lombok.val;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public interface UserStorage {
    Map<Integer, User> getUsers();

    void addUser(User user);

    void updateUser(User user);

    void addToFriends(int id, int friendId);

    void deleteFromFriends(int id, int friendId);

    List<User> getFriends(int id);

    List<User> getCommonFriends(int id, int friendId);

    User getUserById(int id);
