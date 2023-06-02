package ru.yandex.practicum.filmorate.exceptions.filmException;

public class GetMostLikedException extends RuntimeException {
    public GetMostLikedException() {
    }

    public GetMostLikedException(String message) {
        super(message);
    }

    public GetMostLikedException(String message, Throwable cause) {
        super(message, cause);
    }
}
