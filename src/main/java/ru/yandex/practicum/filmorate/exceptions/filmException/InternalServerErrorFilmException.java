package ru.yandex.practicum.filmorate.exceptions.filmException;

public class InternalServerErrorFilmException extends RuntimeException {
    public InternalServerErrorFilmException() {
    }

    public InternalServerErrorFilmException(String message) {
        super(message);
    }

    public InternalServerErrorFilmException(String message, Throwable cause) {
        super(message, cause);
    }
}
