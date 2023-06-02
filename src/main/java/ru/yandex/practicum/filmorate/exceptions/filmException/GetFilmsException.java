package ru.yandex.practicum.filmorate.exceptions.filmException;

public class GetFilmsException extends RuntimeException {
    public GetFilmsException() {
    }

    public GetFilmsException(String message) {
        super(message);
    }

    public GetFilmsException(String message, Throwable cause) {
        super(message, cause);
    }
}
