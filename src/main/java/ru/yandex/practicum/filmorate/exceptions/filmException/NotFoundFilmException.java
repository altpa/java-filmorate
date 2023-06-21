package ru.yandex.practicum.filmorate.exceptions.filmException;

public class NotFoundFilmException extends RuntimeException {
    public NotFoundFilmException() {
    }

    public NotFoundFilmException(String message) {
        super(message);
    }

    public NotFoundFilmException(String message, Throwable cause) {
        super(message, cause);
    }
}
