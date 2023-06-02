package ru.yandex.practicum.filmorate.exceptions.userExceptions;

public class GetUsersException extends RuntimeException {
    public GetUsersException() {
    }

    public GetUsersException(String message) {
        super(message);
    }

    public GetUsersException(String message, Throwable cause) {
        super(message, cause);
    }
}
