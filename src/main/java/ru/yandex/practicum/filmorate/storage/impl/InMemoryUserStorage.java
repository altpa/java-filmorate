package ru.yandex.practicum.filmorate.storage.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component("inMemoryUserStorage")
public class InMemoryUserStorage implements UserStorage {
    private final Map<Integer, User> users = new HashMap<>();
    private int idCounter = 1;

    @Override
    public  Map<Integer, User> getUsers() {
        return users;
    }

    @Override
    public void addUser(User user) {
        user.setId(idCounter++);
        users.put(user.getId(), user);
    }

    @Override
    public void updateUser(User user) {
        users.put(user.getId(), user);
    }

    @Override
    public void addToFriends(int id, int friendId) {
        users.get(id).getFriends().add(friendId);
        users.get(friendId).getFriends().add(id);
    }

    @Override
    public void deleteFromFriends(int id, int friendId) {
        users.get(id).getFriends().remove(friendId);
        users.get(friendId).getFriends().remove(id);
    }

    @Override
    public List<User> getFriends(int id) {
        Set<Integer> friendsIds = users.get(id).getFriends();
        List<User> answer = new ArrayList<>();
        for (int friendId: friendsIds) {
            answer.add(users.get(friendId));
        }
        return answer;
    }

    @Override
    public List<User> getCommonFriends(int id, int friendId) {
        return users.values().stream()
                .filter(user -> user.getFriends().contains(id) && user.getFriends().contains(friendId))
                .collect(Collectors.toList());
    }

    @Override
    public User getUserById(int id) {
        return users.get(id);
    }

    @Override
    public int getMaxId() {
        return users.size();
    }
}
