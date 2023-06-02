package ru.yandex.practicum.filmorate.exceptions.userExceptions;

public class UpdateUserException extends RuntimeException {
    public UpdateUserException() {
    }

    public UpdateUserException(String message) {
        super(message);
    }

    public UpdateUserException(String message, Throwable cause) {
        super(message, cause);
    }
}
