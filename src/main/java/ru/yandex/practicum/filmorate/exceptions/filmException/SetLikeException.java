package ru.yandex.practicum.filmorate.exceptions.filmException;

public class SetLikeException extends RuntimeException {
    public SetLikeException() {
    }

    public SetLikeException(String message) {
        super(message);
    }

    public SetLikeException(String message, Throwable cause) {
        super(message, cause);
    }
}
