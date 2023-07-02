package ru.yandex.practicum.filmorate.storage.impl;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Map;

@Component("userDbStorage")
public class UserDbStorage implements UserStorage {
    @Override
    public Map<Integer, User> getUsers() {
        return null;
    }
}
