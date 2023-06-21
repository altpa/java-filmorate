package ru.yandex.practicum.filmorate.exceptions.userExceptions;

public class InternalServerErrorUserException extends RuntimeException {
    public InternalServerErrorUserException() {
    }

    public InternalServerErrorUserException(String message) {
        super(message);
    }

    public InternalServerErrorUserException(String message, Throwable cause) {
        super(message, cause);
    }
}
