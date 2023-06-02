package ru.yandex.practicum.filmorate.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exceptions.userExceptions.AddToFriendsException;
import ru.yandex.practicum.filmorate.exceptions.userExceptions.AddUserException;
import ru.yandex.practicum.filmorate.exceptions.userExceptions.DeleteFromFriendsException;
import ru.yandex.practicum.filmorate.exceptions.userExceptions.GetCommonFriendsException;
import ru.yandex.practicum.filmorate.exceptions.userExceptions.GetUserByIdException;
import ru.yandex.practicum.filmorate.exceptions.userExceptions.GetFriendsException;
import ru.yandex.practicum.filmorate.exceptions.userExceptions.GetUsersException;
import ru.yandex.practicum.filmorate.exceptions.userExceptions.UpdateUserException;

import java.util.Map;

@RestControllerAdvice
public class UserErrorHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleAddToFriends(final AddToFriendsException e) {
        return Map.of("error", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleAddUser(final AddUserException e) {
        return Map.of("error", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleDeleteFromFriends(final DeleteFromFriendsException e) {
        return Map.of("error", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleGetCommonFriends(final GetCommonFriendsException e) {
        return Map.of("error", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleGetFriends(final GetFriendsException e) {
        return Map.of("error", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleGetUserById(final GetUserByIdException e) {
        return Map.of("error", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleGetUsers(final GetUsersException e) {
        return Map.of("error", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleUpdateUser(final UpdateUserException e) {
        return Map.of("error", e.getMessage());
    }

}
