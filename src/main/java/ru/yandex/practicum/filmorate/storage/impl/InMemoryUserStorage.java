package ru.yandex.practicum.filmorate.storage.impl;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import ru.yandex.practicum.filmorate.exceptions.userExceptions.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class InMemoryUserStorage implements UserStorage {
    private final Map<Integer, User> users = new HashMap<>();
    private int idCounter = 1;
    UserService userService;

    public InMemoryUserStorage() {
        userService = new UserService();
    }

    @Override
    public User addUser(User user) {
        if (user == null) {
            throw new AddUserException("Пустой запрос");
        }
        log.info("Получен POST запрос для добавления пользователя: {}", user);
        userService.checkEmptyName(user);
        user.setId(idCounter++);
        users.put(user.getId(), user);
        log.info("Пользователь добавлен: {}", user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        if (user == null) {
            throw new UpdateUserException("Пустой запрос");
        }
        if (!users.containsKey(user.getId())) {
            throw new UpdateUserException("Такого пользователя нет id: " + user.getId() + ", некого обновлять");
        }
        log.info("Получен PUT запрос для обновления пользователя: {},", user);
        return userService.checkUser(users, user);
    }

    @Override
    public List<User> getUsers() {
        log.info("Получен GET запрос для списка пользователей");
        val answer = new ArrayList<>(this.users.values());
        log.info("Отправлен список пользователей: {}", answer);
        return answer;
    }

    @Override
    public User addToFriends(int id, int friendId) {
        if (users.get(id) == null) {
            throw new AddToFriendsException("Пользователя с id: " + id + " нет, не к кому добавлять друга");
        }
        if (users.get(friendId) == null) {
            throw new AddToFriendsException("Пользователя с id: " + friendId + " нет, некого добавлять в друзья");
        }
        log.info("Получен PUT запрос для добавления ползьзователя {} в друзья {}", users.get(friendId), users.get(id));
        users.get(id).getFriends().add(friendId);
        users.get(friendId).getFriends().add(id);
        log.info("Пользователь {} добавлен в друзя пользователю {}", users.get(friendId), users.get(id));
        return users.get(id);
    }

    @Override
    public User deleteFromFriends(int id, int friendId) {
        if (users.get(id) == null) {
            throw new AddToFriendsException("Пользователя с id: " + id + " нет, не у кого удалять друга");
        }
        log.info("Получен DELETE запрос для удаления пользователя {} из друзей пользователя {}", users.get(friendId), users.get(id));
        users.get(id).getFriends().remove(friendId);
        users.get(friendId).getFriends().remove(id);
        log.info("Пользователь {} удален из друзей пользователя {}", users.get(friendId), users.get(id));
        return users.get(id);
    }

    @Override
    public List<User> getFriends(int id) {
        if (users.get(id) == null) {
            throw new GetFriendsException("Такого пользователя нет");
        }
        log.info("Получен GET запрос для списка друзей пользователя {}", users.get(id));
        Set<Integer> friendsIds = users.get(id).getFriends();
        List<User> answer = new ArrayList<>();
        for (int friendId: friendsIds) {
            answer.add(users.get(friendId));
        }
        log.info("Отправлен список друзей: {}", answer);
        return answer;
    }

    @Override
    public List<User> getCommonFriends(int id, int friendId) {
        if (users.get(id) == null || users.get(friendId) == null) {
            throw new GetCommonFriendsException("Такого пользователя нет");
        }
        log.info("Получен GET запрос для списка общих друзей пользователя {} и пользователя {}", users.get(id), users.get(friendId));
        List<User> answer = users.values().stream()
                .filter(user -> user.getFriends().contains(id) && user.getFriends().contains(friendId))
                .collect(Collectors.toList());
        log.info("Отправлен список общих друзей: {}", answer);
        return answer;
    }

    @Override
    public User getUserById(int id) {
        if (users.get(id) == null) {
            throw new GetUserByIdException("Такого пользователя нет");
        }
        return users.get(id);
    }
}
