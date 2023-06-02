package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.userExceptions.UpdateUserException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Map;

//добавление в друзья, удаление из друзей, вывод списка общих друзей
@Slf4j
@Service
public class UserService {
    public void checkEmptyName(User user) {
        log.info("Начата проверка имени пользователя с логином {}", user.getLogin());
        if (user.getName().isEmpty()) {
            user.setName(user.getLogin());
            log.info("Пользователю {} присвоен логин как имя", user.getLogin());
        }
        log.info("Пользователь {} прошел проверку имени: {}", user.getName(), user);
    }

    public User checkUser(Map<Integer, User> users, User user) {
        if (users.containsKey(user.getId())) {
            log.info("Обновлен будет пользователь: {}", users.get(user.getId()));
            users.put(user.getId(), user);
            log.info("Данные пользователя обновлены");
        } else {
            log.error("Ошибка обновления пользователя.");
            throw new UpdateUserException("Пользователя с id=" + user.getId() + " нет. Невозможно обновить");
        }
        return user;
    }
}
