package ru.yandex.practicum.filmorate.exceptions.userExceptions;

public class GetUserByIdException extends RuntimeException {
    public GetUserByIdException() {
    }

    public GetUserByIdException(String message) {
        super(message);
    }

    public GetUserByIdException(String message, Throwable cause) {
        super(message, cause);
    }
}
