package ru.yandex.practicum.filmorate.exceptions.filmException;

public class GetFilmByIdException extends RuntimeException {
    public GetFilmByIdException() {
    }

    public GetFilmByIdException(String message) {
        super(message);
    }

    public GetFilmByIdException(String message, Throwable cause) {
        super(message, cause);
    }
}
