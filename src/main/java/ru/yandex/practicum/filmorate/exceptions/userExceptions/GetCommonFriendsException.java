package ru.yandex.practicum.filmorate.exceptions.userExceptions;

public class GetCommonFriendsException extends RuntimeException {
    public GetCommonFriendsException() {
    }

    public GetCommonFriendsException(String message) {
        super(message);
    }

    public GetCommonFriendsException(String message, Throwable cause) {
        super(message, cause);
    }
}
