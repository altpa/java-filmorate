package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql({"/schema.sql", "/test-data.sql"})
class UserStorageTest {

    @Autowired
    @Qualifier("userDbStorage")
    private UserStorage userStorage;

    @Test
    public void testUserStorage() {
        assertNotNull(userStorage);
    }

    @Test
    public void getUsers() {
        assertThat(userStorage.getUsers().size()).isEqualTo(3);
    }

    @Test
    public void addUser() {
        User newUser = new User(0,"New user name", "newEmail@mail.com", "New_login",
                LocalDate.of(1900, 10, 10));
        userStorage.addUser(newUser);
        assertThat(userStorage.getUsers().get(4)).hasFieldOrPropertyWithValue("name", "New user name");
    }

    @Test
    public void updateUser() {
        User updateUser = new User(1,"Updated user name", "UpdatedEmail@mail.com", "Updated_login",
                LocalDate.of(1900, 10, 10));
        userStorage.updateUser(updateUser);
        assertThat(userStorage.getUserById(1)).hasFieldOrPropertyWithValue("name", "Updated user name");
    }

    @Test
    public void addToFriend() {
        userStorage.addToFriends(3, 1);
        assertThat(userStorage.getFriends(3).size()).isEqualTo(1);
    }

    @Test
    public void  deleteFromFriends() {
        userStorage.deleteFromFriends(2, 1);
        assertThat(userStorage.getFriends(2).size()).isEqualTo(0);
    }


    @Test
    public void getFriends() {
        assertThat(userStorage.getFriends(1).size()).isEqualTo(2);
    }

    @Test
    public void getCommonFriends() {
        userStorage.addToFriends(2, 3);
        assertThat(userStorage.getCommonFriends(1, 2).size()).isEqualTo(1);
    }

    @Test
    public void getUserById() {
        assertThat(userStorage.getUserById(1)).hasFieldOrPropertyWithValue("name", "User Name1");
    }

    @Test
    public void getMaxId() {
        assertThat(userStorage.getMaxId()).isEqualTo(3);
    }
}
