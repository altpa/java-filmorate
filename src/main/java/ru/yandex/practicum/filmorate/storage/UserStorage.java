package ru.yandex.practicum.filmorate.storage;

import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.Map;

public interface UserStorage {
    void addUser(int id, @Valid @RequestBody User user);

    Map<Integer, User> getUsers();
}
