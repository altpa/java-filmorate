package ru.yandex.practicum.filmorate.model;

import lombok.*;
import ru.yandex.practicum.filmorate.validatorAnnotations.spacesValidatorAnnotations.NoSpaces;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class User {
    private int id;
    private String name;
    @Email(message = "Ошибка в email пользователя")
    private final String email;
    @NotBlank(message = "Логин не может быть пустым")
    @NoSpaces
    private final String login;
    @Past(message = "Ошибка в дате рождения")
    private final LocalDate birthday;
}