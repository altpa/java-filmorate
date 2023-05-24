package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.utils.ControllerUtil;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {
    private final Map<Integer, User> users = new HashMap<>();
    private int idCounter = 1;

    @PostMapping
    public User addUser(@Valid @RequestBody User user) {
        log.info("Получен POST запрос для добавления пользователя: {}", user);
        ControllerUtil.checkEmptyName(user);
        user.setId(idCounter++);
        users.put(user.getId(), user);
        log.info("Пользователь добавлен: {}", user);
        return user;
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        log.info("Получен PUT запрос для обновления пользователя: {},", user);
        return ControllerUtil.checkUser(users, user);
    }

    @GetMapping
    public List<User> getUsers() {
        log.info("Получен GET запрос для списка пользователей");
        val answer = new ArrayList<>(this.users.values());
        log.info("Отправлен список пользователей: {}", answer);
        return answer;
    }
}
