package ru.yandex.practicum.filmorate.validation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.filmException.BadRequestFilmException;
import ru.yandex.practicum.filmorate.exceptions.userExceptions.InternalServerErrorUserException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Map;

@Slf4j
@Component
public class Validation {
    public static void checkEmptyName(User user) {
        log.info("Начата проверка имени пользователя с логином {}", user.getLogin());
        if (user.getName().isEmpty()) {
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
            throw new InternalServerErrorUserException("Пользователя с id=" + user.getId() + " нет. Невозможно обновить");
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
            throw new BadRequestFilmException("Фильма с id=" + film.getId() + " нет. Невозможно обновить");
        }
        return film;
    }
}
