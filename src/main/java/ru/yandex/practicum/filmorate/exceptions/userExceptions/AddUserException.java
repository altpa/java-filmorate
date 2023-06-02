package ru.yandex.practicum.filmorate.exceptions.userExceptions;

public class AddUserException extends RuntimeException {
    public AddUserException() {
    }

    public AddUserException(String message) {
        super(message);
    }

    public AddUserException(String message, Throwable cause) {
        super(message, cause);
    }
}
