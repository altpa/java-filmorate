package ru.yandex.practicum.filmorate.exceptions.userExceptions;

public class GetFriendsException extends RuntimeException {
    public GetFriendsException() {
    }

    public GetFriendsException(String message) {
        super(message);
    }

    public GetFriendsException(String message, Throwable cause) {
        super(message, cause);
    }
}
