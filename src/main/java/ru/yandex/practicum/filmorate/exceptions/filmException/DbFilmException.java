package ru.yandex.practicum.filmorate.exceptions.filmException;

public class DbFilmException extends RuntimeException {
    public DbFilmException() {
    }

    public DbFilmException(String message) {
        super(message);
    }

    public DbFilmException(String message, Throwable cause) {
        super(message, cause);
    }
}
