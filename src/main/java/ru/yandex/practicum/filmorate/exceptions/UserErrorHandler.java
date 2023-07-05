package ru.yandex.practicum.filmorate.exceptions;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exceptions.userExceptions.BadRequestUserException;
import ru.yandex.practicum.filmorate.exceptions.userExceptions.InternalServerErrorUserException;
import ru.yandex.practicum.filmorate.exceptions.userExceptions.NotFoundUserException;

import java.util.Map;

@RestControllerAdvice
public class UserErrorHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNotFoundUserException(final NotFoundUserException e) {
        return Map.of("error", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleBadRequestUserException(final BadRequestUserException e) {
        return Map.of("error", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleInternalServerErrorUserException(final InternalServerErrorUserException e) {
        return Map.of("error", e.getMessage());
    }

//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.OK)
//    public Map<String, String> handleInternalServerErrorFilmException(final EmptyResultDataAccessException e) {
//        return Map.of("UserDB", e.getMessage());
//    }
}
