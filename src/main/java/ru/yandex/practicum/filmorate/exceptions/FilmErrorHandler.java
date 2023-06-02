package ru.yandex.practicum.filmorate.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exceptions.filmException.AddFilmException;
import ru.yandex.practicum.filmorate.exceptions.filmException.DeleteLikeException;
import ru.yandex.practicum.filmorate.exceptions.filmException.GetFilmByIdException;
import ru.yandex.practicum.filmorate.exceptions.filmException.GetFilmsException;
import ru.yandex.practicum.filmorate.exceptions.filmException.GetMostLikedException;
import ru.yandex.practicum.filmorate.exceptions.filmException.SetLikeException;
import ru.yandex.practicum.filmorate.exceptions.filmException.UpdateFilmException;

import java.util.Map;

@RestControllerAdvice
public class FilmErrorHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleAddFilm(final AddFilmException e) {
        return Map.of("error", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleDeleteLike(final DeleteLikeException e) {
        return Map.of("error", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleGetFilmById(final GetFilmByIdException e) {
        return Map.of("error", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleGetFilms(final GetFilmsException e) {
        return Map.of("error", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleGetMostLiked(final GetMostLikedException e) {
        return Map.of("error", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleSetLike(final SetLikeException e) {
        return Map.of("error", e.getMessage());
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleUpdateFilm(final UpdateFilmException e) {
        return Map.of("error", e.getMessage());
    }
}
