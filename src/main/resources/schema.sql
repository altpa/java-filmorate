drop table if exists rating, genre, film_genre_list, films, users, likes, friends cascade;

CREATE TABLE IF NOT EXISTS rating
(
	rating_id int generated by default as identity primary key,
	rating_name varchar(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS genre
(
	genre_id int generated by default as identity primary key,
	genre_name varchar(255) not null unique
);

CREATE TABLE IF NOT EXISTS film_genre_list
(
	film_genre_list_id int generated by default as identity primary key,
	genre_id int not null REFERENCES genre (genre_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS films
(
	film_id int generated by default as identity primary key,
	name varchar(255) NOT NULL,
	description varchar(500) DEFAULT 'Нет описания.',
	release_date timestamp,
	duration int,
	rate real,
	rating_id int REFERENCES rating (rating_id) ON DELETE CASCADE,
	genre_list int REFERENCES film_genre_list (film_genre_list_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS users
(
	user_id int generated by default as identity primary key,
	name varchar(255) NOT NULL,
	email varchar(255) NOT NULL,
	login varchar(255) NOT NULL,
	birthday timestamp
);

CREATE TABLE IF NOT EXISTS likes
(
	film_id int REFERENCES films (film_id) ON DELETE CASCADE,
	user_id int REFERENCES users (user_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS friends
(
	user_id int REFERENCES users (user_id) ON DELETE CASCADE,
	friend_id int REFERENCES users (user_id) ON DELETE CASCADE
);