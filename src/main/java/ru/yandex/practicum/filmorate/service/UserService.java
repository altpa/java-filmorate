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
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {
    private final UserAndFilmValidation validator;
    private int idCounter = 1;
    Map<Integer, User> users;

    @Autowired
    public UserService(@Qualifier("inMemoryUserStorage") UserStorage userStorage, UserAndFilmValidation validation) {
        this.validator = validation;
        users = userStorage.getUsers();
    }

    public User addUser(User user) {
        validator.checkUserRequest(user);
        validator.checkEmptyName(user);
        user.setId(idCounter++);
        users.put(user.getId(), user);
        return user;
    }

    public User updateUser(User user) {
        validator.checkUserRequest(user);
        validator.checkUsersContainUser(users, user);
        log.info("Обновлен будет пользователь: {}", users.get(user.getId()));
        users.put(user.getId(), user);
        return user;
    }

    public User addToFriends(int id, int friendId) {
        validator.checkUserNull(users, users.get(id));
        validator.checkUserNull(users, users.get(friendId));
        users.get(id).getFriends().add(friendId);
        users.get(friendId).getFriends().add(id);
        return users.get(id);
    }

    public User deleteFromFriends(int id, int friendId) {
        validator.checkUserNull(users, users.get(id));
        users.get(id).getFriends().remove(friendId);
        users.get(friendId).getFriends().remove(id);
        return users.get(id);
    }

    public List<User> getFriends(int id) {
        validator.checkUserNull(users, users.get(id));
        Set<Integer> friendsIds = users.get(id).getFriends();
        List<User> answer = new ArrayList<>();
        for (int friendId: friendsIds) {
            answer.add(users.get(friendId));
        }
        return answer;
    }

    public List<User> getCommonFriends(int id, int friendId) {
        validator.checkUserNull(users, users.get(id));
        validator.checkUserNull(users, users.get(friendId));
        log.info("Получен GET запрос для списка общих друзей пользователя {} и пользователя {}", users.get(id), users.get(friendId));
        List<User> answer = users.values().stream()
                .filter(user -> user.getFriends().contains(id) && user.getFriends().contains(friendId))
                .collect(Collectors.toList());
        log.info("Отправлен список общих друзей: {}", answer);
        return answer;
    }

    public User getUserById(int id) {
        validator.checkUserNull(users, users.get(id));
        return users.get(id);
    }

    public List<User> getUsers() {
        val answer = new ArrayList<>(users.values());
        return answer;
    }
}
