package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.RatingStorage;
import ru.yandex.practicum.filmorate.validation.MpaValidation;

import java.util.List;

@Slf4j
@Service
public class MpaService {
    private final RatingStorage ratings;
    private final MpaValidation validation;

    @Autowired
    public MpaService(RatingStorage ratings, MpaValidation validation) {
        this.ratings = ratings;
        this.validation = validation;
    }

    public List<Mpa> getRatings() {
        return ratings.getRatings();
    }

    public Mpa getRatingById(int id) {
        validation.checkMpaExist(ratings, id);
        return ratings.getRatingById(id);
    }
}
