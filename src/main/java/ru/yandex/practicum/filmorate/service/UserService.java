package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
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
        return users.getUserById(users.getMaxId()).get();
    }

    public User updateUser(User user) {
        validator.checkUserRequest(user);
        validator.checkUserNull(users.getUserById(user.getId()));
        log.info("Обновлен будет пользователь: {}", user);
        users.updateUser(user);
        return users.getUserById(user.getId()).get();
    }

    public User addToFriends(int id, int friendId) {
        validator.checkUserNull(users.getUserById(id));
        validator.checkUserNull(users.getUserById(friendId));
        users.addToFriends(id, friendId);
        return users.getUserById(id).get();
    }

    public User deleteFromFriends(int id, int friendId) {
        validator.checkUserNull(users.getUserById(id));
        users.deleteFromFriends(id, friendId);
        return users.getUserById(id).get();
    }

    public List<User> getFriends(int id) {
        validator.checkUserNull(users.getUserById(id));
        return users.getFriends(id);
    }

    public List<User> getCommonFriends(int id, int friendId) {
        validator.checkUserNull(users.getUserById(id));
        validator.checkUserNull(users.getUserById(friendId));
        return users.getCommonFriends(id, friendId);
    }

    public User getUserById(int id) {
        validator.checkUserNull(users.getUserById(id));
        return users.getUserById(id).get();
    }

    public List<User> getUsers() {
        return new ArrayList<>(users.getUsers().values());
    }
}
