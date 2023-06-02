package ru.yandex.practicum.filmorate.exceptions.userExceptions;

public class DeleteFromFriendsException extends RuntimeException {
    public DeleteFromFriendsException() {
    }

    public DeleteFromFriendsException(String message) {
        super(message);
    }

    public DeleteFromFriendsException(String message, Throwable cause) {
        super(message, cause);
    }
}
