package ru.yandex.practicum.filmorate.exceptions.genreExceptions;

public class NotFoundGenreException extends RuntimeException {
    public NotFoundGenreException() {
    }

    public NotFoundGenreException(String message) {
        super(message);
    }

    public NotFoundGenreException(String message, Throwable cause) {
        super(message, cause);
    }
}
