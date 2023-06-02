package ru.yandex.practicum.filmorate.exceptions.userExceptions;

public class AddToFriendsException extends RuntimeException {
    public AddToFriendsException() {
    }

    public AddToFriendsException(String message) {
        super(message);
    }

    public AddToFriendsException(String message, Throwable cause) {
        super(message, cause);
    }
}
