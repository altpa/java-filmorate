package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.RatingStorage;

import java.util.List;

@Slf4j
@Service
public class MpaService {
    private final RatingStorage ratings;

    @Autowired
    public MpaService(RatingStorage ratings) {
        this.ratings = ratings;
    }

    public List<Mpa> getRatings() {
        return ratings.getRatings();
    }

    public Mpa getRatingById(int id) {
        return ratings.getRatingById(id);
    }
}
