package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.List;

@Component
public interface UserStorage {
    User addUser(@Valid @RequestBody User user);

    User updateUser(@Valid @RequestBody User user);

    List<User> getUsers();

    User getUserById(int id);

    User addToFriends(int id, int friendId);

    User deleteFromFriends(int id, int friendId);

    List<User> getFriends(int id);

    List<User> getCommonFriends(int id, int friendId);
}
