package ru.yandex.practicum.filmorate.exceptions.genreExceptions;

public class DbGenreException extends RuntimeException {
    public DbGenreException() {
    }

    public DbGenreException(String message) {
        super(message);
    }

    public DbGenreException(String message, Throwable cause) {
        super(message, cause);
    }
}
