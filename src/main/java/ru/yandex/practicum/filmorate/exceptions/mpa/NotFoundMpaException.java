package ru.yandex.practicum.filmorate.exceptions.mpa;

public class NotFoundMpaException extends RuntimeException {
    public NotFoundMpaException() {
    }

    public NotFoundMpaException(String message) {
        super(message);
    }

    public NotFoundMpaException(String message, Throwable cause) {
        super(message, cause);
    }
}
