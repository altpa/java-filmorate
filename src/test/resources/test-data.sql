INSERT INTO rating (rating_name)
    VALUES
        ('G'), ('PG'), ('PG-13'), ('R'), ('NC-17');

INSERT INTO genre (genre_name)
    VALUES
        ('Комедия'), ('Драма'), ('Мультфильм'), ('Триллер'), ('Документальный'), ('Боевик');

INSERT INTO USERS (NAME, EMAIL, LOGIN, BIRTHDAY)
    values
        ('User Name1', 'emailUser1@mail.com', 'login1', '2000-10-11'),
        ('User Name2', 'emailUser2@mail.com', 'login2', '2000-10-12'),
        ('User Name3', 'emailUser3@mail.com', 'login3', '2000-10-13');

INSERT INTO FILMS (NAME, DESCRIPTION, RELEASE_DATE, DURATION, RATE, RATING_ID)
    VALUES
        ('Film Name1', 'Description1', '2000-10-11', '120', '0', '1'),
        ('Film Name2', 'Description2', '2000-10-12', '140', '0', '2');

INSERT INTO FRIENDS (USER_ID, FRIEND_ID)
    VALUES
        (1, 2),
        (2, 1),
        (1, 3);

INSERT INTO FILM_GENRE_LIST (FILM_ID, GENRE_ID)
    VALUES
        (1, 2),
        (1, 1),
        (1, 5),
        (2, 1);

--INSERT INTO LIKES (FILM_ID, USER_ID)
--    VALUES
--        (1, 1),
--        (1, 2),
--        (2, 1);