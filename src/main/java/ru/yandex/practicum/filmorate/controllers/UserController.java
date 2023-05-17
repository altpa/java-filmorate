package ru.yandex.practicum.filmorate.controllers;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    private final Map<Integer, User> users = new HashMap<>();

    @PostMapping
    public User create(@RequestBody User user) {
        users.put(user.getId(), user);
        return user;
    }

    @PutMapping
    public User update(@RequestBody User user) {
        users.put(user.getId(), user);
        return user;
    }

    @GetMapping
    public Map<Integer, User> get(){
        return users;
    }
//    создание пользователя; POST
//    обновление пользователя; PUT
//    получение списка всех пользователей. GET

//    электронная почта не может быть пустой и должна содержать символ @;
//    логин не может быть пустым и содержать пробелы;
//    имя для отображения может быть пустым — в таком случае будет использован логин;
//    дата рождения не может быть в будущем.
}
