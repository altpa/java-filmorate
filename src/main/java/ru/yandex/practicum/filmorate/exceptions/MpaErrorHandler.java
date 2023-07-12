package ru.yandex.practicum.filmorate.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exceptions.filmException.InternalServerErrorFilmException;
import ru.yandex.practicum.filmorate.exceptions.mpaExceptions.DbMpaException;

import java.util.Map;

@RestControllerAdvice
public class MpaErrorHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNotFoundException(final DbMpaException e) {
        return Map.of("error", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleInternalServerErrorFilmException(final InternalServerErrorFilmException e) {
        return Map.of("error", e.getMessage());
    }
}
