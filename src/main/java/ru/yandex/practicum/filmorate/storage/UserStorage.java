package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserStorage {
    Map<Integer, User> getUsers();

    void addUser(User user);

    void updateUser(User user);

    void addToFriends(int id, int friendId);

    void deleteFromFriends(int id, int friendId);

    List<User> getFriends(int id);

    List<User> getCommonFriends(int id, int friendId);

    Optional<User> getUserById(int id);

    int getMaxId();
}
