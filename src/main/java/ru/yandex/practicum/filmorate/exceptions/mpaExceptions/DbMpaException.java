package ru.yandex.practicum.filmorate.exceptions.mpaExceptions;

public class DbMpaException extends RuntimeException {
    public DbMpaException() {
    }

    public DbMpaException(String message) {
        super(message);
    }

    public DbMpaException(String message, Throwable cause) {
        super(message, cause);
    }
}
