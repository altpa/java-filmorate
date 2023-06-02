package ru.yandex.practicum.filmorate.exceptions.filmException;

public class AddFilmException extends RuntimeException {
    public AddFilmException() {
    }

    public AddFilmException(String message) {
        super(message);
    }

    public AddFilmException(String message, Throwable cause) {
        super(message, cause);
    }
}
