package ru.yandex.practicum.filmorate.exceptions.filmException;

public class BadRequestFilmException extends RuntimeException {
    public BadRequestFilmException() {
    }

    public BadRequestFilmException(String message) {
        super(message);
    }

    public BadRequestFilmException(String message, Throwable cause) {
        super(message, cause);
    }
}
