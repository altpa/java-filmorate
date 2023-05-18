package ru.yandex.practicum.filmorate.model.impl;

import lombok.*;
import ru.yandex.practicum.filmorate.model.Model;
import ru.yandex.practicum.filmorate.model.spacesValidatorAnnotations.NoSpaces;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Getter @Setter
public class User extends Model {
    @Email(message = "Ошибка в email пользователя")
    private final String email;
    @NotBlank(message = "Логин не может быть пустым")
    @NoSpaces
    private final String login;
    @Past(message = "Ошибка в дате рождения")
    private final LocalDate birthday;
    @Builder
    public User(int id, String email, String login, String name, LocalDate birthday) {
        super(id, name);
        this.email = email;
        this.login = login;
        this.birthday = birthday;
    }
}