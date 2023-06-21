package ru.yandex.practicum.filmorate.exceptions.userExceptions;

public class NotFoundUserException extends RuntimeException {
    public NotFoundUserException() {
    }

    public NotFoundUserException(String message) {
        super(message);
    }

    public NotFoundUserException(String message, Throwable cause) {
        super(message, cause);
    }
}
