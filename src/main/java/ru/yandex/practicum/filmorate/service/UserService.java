package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.userExceptions.BadRequestUserException;
import ru.yandex.practicum.filmorate.exceptions.userExceptions.InternalServerErrorUserException;
import ru.yandex.practicum.filmorate.exceptions.userExceptions.NotFoundUserException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.validation.Validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserStorage userStorage;
    private int idCounter = 1;

    public User addUser(User user) {
        if (user == null) {
            throw new BadRequestUserException("Пустой запрос");
        }
        log.info("Получен POST запрос для добавления пользователя: {}", user);
        Validation.checkEmptyName(user);
        user.setId(idCounter++);
        userStorage.addUser(user.getId(), user);
        log.info("Пользователь добавлен: {}", user);
        return user;
    }

    public User updateUser(User user) {
        if (user == null) {
            throw new InternalServerErrorUserException("Пустой запрос");
        }
        if (!userStorage.getUsers().containsKey(user.getId())) {
            throw new InternalServerErrorUserException("Такого пользователя нет id: " + user.getId() + ", некого обновлять");
        }
        log.info("Получен PUT запрос для обновления пользователя: {},", user);
        return Validation.checkUser(userStorage.getUsers(), user);
    }

    public User addToFriends(int id, int friendId) {
        if (userStorage.getUsers().get(id) == null) {
            throw new NotFoundUserException("Пользователя с id: " + id + " нет, не к кому добавлять друга");
        }
        if (userStorage.getUsers().get(friendId) == null) {
            throw new NotFoundUserException("Пользователя с id: " + friendId + " нет, некого добавлять в друзья");
        }
        log.info("Получен PUT запрос для добавления ползьзователя {} в друзья {}", userStorage.getUsers().get(friendId), userStorage.getUsers().get(id));
        userStorage.getUsers().get(id).getFriends().add(friendId);
        userStorage.getUsers().get(friendId).getFriends().add(id);
        log.info("Пользователь {} добавлен в друзя пользователю {}", userStorage.getUsers().get(friendId), userStorage.getUsers().get(id));
        return userStorage.getUsers().get(id);
    }

    public User deleteFromFriends(int id, int friendId) {
        if (userStorage.getUsers().get(id) == null) {
            throw new BadRequestUserException("Пользователя с id: " + id + " нет, не у кого удалять друга");
        }
        log.info("Получен DELETE запрос для удаления пользователя {} из друзей пользователя {}", userStorage.getUsers().get(friendId), userStorage.getUsers().get(id));
        userStorage.getUsers().get(id).getFriends().remove(friendId);
        userStorage.getUsers().get(friendId).getFriends().remove(id);
        log.info("Пользователь {} удален из друзей пользователя {}", userStorage.getUsers().get(friendId), userStorage.getUsers().get(id));
        return userStorage.getUsers().get(id);
    }

    public List<User> getFriends(int id) {
        if (userStorage.getUsers().get(id) == null) {
            throw new BadRequestUserException("Такого пользователя нет");
        }
        log.info("Получен GET запрос для списка друзей пользователя {}", userStorage.getUsers().get(id));
        Set<Integer> friendsIds = userStorage.getUsers().get(id).getFriends();
        List<User> answer = new ArrayList<>();
        for (int friendId: friendsIds) {
            answer.add(userStorage.getUsers().get(friendId));
        }
        log.info("Отправлен список друзей: {}", answer);
        return answer;
    }

    public List<User> getCommonFriends(int id, int friendId) {
        if (userStorage.getUsers().get(id) == null || userStorage.getUsers().get(friendId) == null) {
            throw new BadRequestUserException("Такого пользователя нет");
        }
        log.info("Получен GET запрос для списка общих друзей пользователя {} и пользователя {}", userStorage.getUsers().get(id), userStorage.getUsers().get(friendId));
        List<User> answer = userStorage.getUsers().values().stream()
                .filter(user -> user.getFriends().contains(id) && user.getFriends().contains(friendId))
                .collect(Collectors.toList());
        log.info("Отправлен список общих друзей: {}", answer);
        return answer;
    }

    public User getUserById(int id) {
        if (userStorage.getUsers().get(id) == null) {
            throw new NotFoundUserException("Такого пользователя нет");
        }
        return userStorage.getUsers().get(id);
    }

    public List<User> getUsers() {
        log.info("Получен GET запрос для списка пользователей");
        val answer = new ArrayList<>(userStorage.getUsers().values());
        log.info("Отправлен список пользователей: {}", answer);
        return answer;
    }
}
