package ru.yandex.practicum.filmorate.utils;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Map;

@Slf4j
public class ControllerUtil {
    public static void checkEmptyName(User user) {
        log.info("Начата проверка имени пользователя с логином {}", user.getLogin());
        if (user.getName() == null) {
            user.setName(user.getLogin());
            log.info("Пользователю {} присвоен логин как имя", user.getLogin());
        }
        log.info("Пользователь {} прошел проверку имени: {}", user.getName(), user);
    }

    public static User checkUser(Map<Integer, User> users, User user) {
        if (users.containsKey(user.getId())) {
            log.info("Обновлен будет пользователь: {}", users.get(user.getId()));
            users.put(user.getId(), user);
            log.info("Данные пользователя обновлены");
        } else {
            log.error("Ошибка обновления пользователя.");
            throw new ValidationException("Пользователя с id=" + user.getId() + " нет. Невозможно обновить");
        }
        return user;
    }

    public static Film checkFilm(Map<Integer, Film> films, Film film) {
        if (films.containsKey(film.getId())) {
            log.info("Будет обновлен фильм {}", films.get(film.getId()));
            films.put(film.getId(), film);
            log.info("Данные фильма обновлены");
        } else {
            log.error("Ошибка обновления фильма.");
            throw new ValidationException("Фильма с id=" + film.getId() + " нет. Невозможно обновить");
        }
        return film;
    }
}
