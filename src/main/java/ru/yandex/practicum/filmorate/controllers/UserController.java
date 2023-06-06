package ru.yandex.practicum.filmorate.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public User addUser(@Valid @RequestBody User user) {
        log.info("Получен POST запрос для добавления пользователя: {}", user);
        val answer = userService.addUser(user);
        log.info("Пользователь добавлен: {}", answer);
        return answer;
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        log.info("Получен PUT запрос для обновления пользователя: {},", user);
        val answer = userService.updateUser(user);
        log.info("Данные пользователя {} обновлены", answer);
        return answer;
    }

    @GetMapping
    public List<User> getUsers() {
        log.info("Получен GET запрос для списка пользователей");
        val answer = userService.getUsers();
        log.info("Отправлен список пользователей: {}", answer);
        return answer;
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id) {
        log.info("Получен GET запрос для юзера id:{} пользователей", id);
        val answer = userService.getUserById(id);
        log.info("Юзер {} отправлен", answer);
        return answer;
    }

    @PutMapping("/{id}/friends/{friendId}")
    public User addToFriends(@PathVariable int id, @PathVariable int friendId) {
        log.info("Получен PUT запрос для добавления ползьзователя id:{} в друзья id:{}", id, friendId);
        val answer = userService.addToFriends(id, friendId);
        log.info("Пользователь id:{} добавлен в друзя пользователю {}", id, answer);
        return answer;
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public User deleteFromFriends(@PathVariable int id, @PathVariable int friendId) {
        log.info("Получен DELETE запрос для удаления пользователя id:{} из друзей пользователя id:{}", id, friendId);
        val answer = userService.deleteFromFriends(id, friendId);
        log.info("Пользователь id:{} удален из друзей пользователя {}", friendId, answer);
        return answer;
    }

    @GetMapping("/{id}/friends")
    public List<User> getFriends(@PathVariable int id) {
        log.info("Получен GET запрос для списка друзей пользователя id:{}", id);
        val answer = userService.getFriends(id);
        log.info("Отправлен список друзей: {}", answer);
        return answer;
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable int id, @PathVariable int otherId) {
        log.info("Получен GET запрос для списка общих друзей пользователя id:{} и пользователя id:{}", id, otherId);
        val answer = userService.getCommonFriends(id, otherId);
        log.info("Отправлен список общих друзей: {}", answer);
        return answer;
    }
}
