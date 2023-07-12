package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;

public interface RatingStorage {
    List<Mpa> getRatings();

    Mpa getRatingById(int id);

    List<Integer> getMpaIds();
}
