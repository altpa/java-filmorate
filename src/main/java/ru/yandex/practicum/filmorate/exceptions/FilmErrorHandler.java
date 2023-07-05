package ru.yandex.practicum.filmorate.exceptions;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exceptions.filmException.BadRequestFilmException;
import ru.yandex.practicum.filmorate.exceptions.filmException.InternalServerErrorFilmException;
import ru.yandex.practicum.filmorate.exceptions.filmException.NotFoundFilmException;

import java.util.Map;

@RestControllerAdvice
public class FilmErrorHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleBadRequestException(final BadRequestFilmException e) {
        return Map.of("error", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNotFoundException(final NotFoundFilmException e) {
        return Map.of("error", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleInternalServerErrorFilmException(final InternalServerErrorFilmException e) {
        return Map.of("error", e.getMessage());
    }

//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.OK)
//    public Map<String, String> handleInternalServerErrorFilmException(final EmptyResultDataAccessException e) {
//        return Map.of("FilmDB", e.getMessage());
//    }
}
