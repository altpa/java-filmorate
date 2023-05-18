package ru.yandex.practicum.filmorate.controllers.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.controllers.Controller;
import ru.yandex.practicum.filmorate.model.impl.User;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController extends Controller {
    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
    public User create(@RequestBody @Valid User user) {
        log.info("Получен запрос для пользователя");
        checkEmptyName(user);
        add(user);
        return user;
    }

    private void checkEmptyName(User user) {
        log.info("Начата проверка имени пользователя с логином {}", user.getLogin());
        if (user.getName() == null) {
            user.setName(user.getLogin());
        }
        log.info("Пользователь {} прошел проверку имени", user.getName());
    }
}
