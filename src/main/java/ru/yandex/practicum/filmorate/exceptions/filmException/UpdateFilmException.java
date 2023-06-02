package ru.yandex.practicum.filmorate.exceptions.filmException;

public class UpdateFilmException extends RuntimeException {
    public UpdateFilmException() {
    }

    public UpdateFilmException(String message) {
        super(message);
    }

    public UpdateFilmException(String message, Throwable cause) {
        super(message, cause);
    }
}
