package ru.yandex.practicum.filmorate.exceptions.filmException;

public class DeleteLikeException extends RuntimeException {
    public DeleteLikeException() {
    }

    public DeleteLikeException(String message) {
        super(message);
    }

    public DeleteLikeException(String message, Throwable cause) {
        super(message, cause);
    }
}
