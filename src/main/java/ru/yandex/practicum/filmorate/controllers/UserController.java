package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

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
    public User addUser(@RequestBody @Valid User user) {
        log.info("Получен POST запрос для добавления пользователя {}", user.getName());
        checkEmptyName(user);
        user.setId(idCounter++);
        users.put(user.getId(), user);
        log.info("Пользователь {} добавлен. id={}", user.getName(), user.getId());
        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody @Valid User user) {
        log.info("Получен PUT запрос для обновления пользователя {}, id={}", user.getName(), user.getId());
        if (users.containsKey(user.getId())) {
            log.info("Обновлен будет пользователь {}", users.get(user.getId()).getName());
            users.put(user.getId(), user);
            log.info("Данные пользователя обновлены");
        } else {
            log.error("Ошибка обновления пользователя.");
            throw new ValidationException("Пользователя с id=" + user.getId() + " нет. Невозможно обновить");
        }
        return user;
    }

    @GetMapping
    public List<User> getUsers() {
        log.info("Получен GET запрос для списка пользователей. Всего пользователей: {}", users.size());
        return new ArrayList<>(users.values());
    }

    private void checkEmptyName(User user) {
        log.info("Начата проверка имени пользователя с логином {}", user.getLogin());
        if (user.getName() == null) {
            user.setName(user.getLogin());
            log.info("Пользователю {} присвоен логин как имя", user.getLogin());
        }
        log.info("Пользователь {} прошел проверку имени", user.getName());
    }
}
