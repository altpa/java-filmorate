package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.validation.UserAndFilmValidation;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserService {
    private final UserAndFilmValidation validator;
    private final UserStorage users;

    @Autowired
    public UserService(@Qualifier("userDbStorage") UserStorage userStorage, UserAndFilmValidation validation) {
        this.validator = validation;
        this.users = userStorage;
    }

    public User addUser(User user) {
        validator.checkUserRequest(user);
        validator.checkEmptyName(user);
        users.addUser(user);
        user.setId(users.getUsers().size());
        return user;
    }

    public User updateUser(User user) {
        validator.checkUserRequest(user);
        validator.checkUsersContainUser(users.getUsers(), user);
        log.info("Обновлен будет пользователь: {}", user);
        users.updateUser(user);
        return user;
    }

    public User addToFriends(int id, int friendId) {
        validator.checkUserNull(users.getUsers(), users.getUserById(id));
        validator.checkUserNull(users.getUsers(), users.getUserById(friendId));
        users.addToFriends(id, friendId);
        return users.getUserById(id);
    }

    public User deleteFromFriends(int id, int friendId) {
        validator.checkUserNull(users.getUsers(), users.getUserById(id));
        users.deleteFromFriends(id, friendId);
        return users.getUserById(id);
    }

    public List<User> getFriends(int id) {
        validator.checkUserNull(users.getUsers(), users.getUserById(id));
        return users.getFriends(id);
    }

    public List<User> getCommonFriends(int id, int friendId) {
        validator.checkUserNull(users.getUsers(), users.getUserById(id));
        validator.checkUserNull(users.getUsers(), users.getUserById(friendId));
        log.info("Получен GET запрос для списка общих друзей пользователя {} и пользователя {}", users.getUserById(id), users.getUserById(friendId));
        val answer = users.getCommonFriends(id, friendId);
        log.info("Отправлен список общих друзей: {}", answer);
        return answer;
    }

    public User getUserById(int id) {
        validator.checkUserNull(users.getUsers(), users.getUserById(id));
        return users.getUserById(id);
    }

    public List<User> getUsers() {
        val answer = new ArrayList<>(users.getUsers().values());
        return answer;
    }
}
