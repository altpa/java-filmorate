package ru.yandex.practicum.filmorate.exceptions.userExceptions;

public class BadRequestUserException extends RuntimeException {
    public BadRequestUserException() {
    }

    public BadRequestUserException(String message) {
        super(message);
    }

    public BadRequestUserException(String message, Throwable cause) {
        super(message, cause);
    }
}
