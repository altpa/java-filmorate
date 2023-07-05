package ru.yandex.practicum.filmorate.validation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.filmException.NotFoundFilmException;
import ru.yandex.practicum.filmorate.exceptions.userExceptions.BadRequestUserException;
import ru.yandex.practicum.filmorate.exceptions.userExceptions.NotFoundUserException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Map;

@Slf4j
@Component
public class UserAndFilmValidation {
    public void checkEmptyName(User user) {
        log.info("Начата проверка имени пользователя с логином {}", user.getLogin());
        if (user.getName().isEmpty()) {
            user.setName(user.getLogin());
            log.info("Пользователю {} присвоен логин как имя", user.getLogin());
        }
        log.info("Пользователь {} прошел проверку имени: {}", user.getName(), user);
    }

    public void checkUserNull(Map<Integer, User> users, User user) {
        if (user == null) {
            throw new NotFoundUserException("User == null");
        }
    }

    public void checkUserRequest(User user) {
        if (user == null) {
            throw new BadRequestUserException("Пустой запрос");
        }
    }

    public void checkUsersContainUser(Map<Integer, User> users, User user) {
        if (!users.containsKey(user.getId())) {
            throw new NotFoundUserException("Такого пользователя нет, id: " + user.getId());
        }
    }

    public void checkFilmRequest(Film film) {
        if (film == null) {
            throw new NotFoundFilmException("Пустой запрос");
        }
    }

    public void checkFilmNull(Map<Integer, Film> films, Film film) {
        if (film == null) {
            throw new NotFoundFilmException("Film == null");
        }
    }

    public void checkFilmsContainFilm(Map<Integer, Film> films, Film film) {
        if (!films.containsKey(film.getId())) {
            throw new NotFoundFilmException("Такого фильма нет, id: " + film.getId());
        }
    }

    public void checkFilmsHaveLike(int userId, Film film) {
        if (!film.getLikes().contains(userId)) {
            throw new NotFoundFilmException("У фильма " + film + " нет лайков от id:" + userId);
        }
    }
}
