package ru.yandex.practicum.filmorate.validation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.mpaExceptions.DbMpaException;
import ru.yandex.practicum.filmorate.storage.RatingStorage;

@Slf4j
@Component
public class MpaValidation {

    public void checkMpaExist(RatingStorage ratings, int mpaId) {
        if (!ratings.getMpaIds().contains(mpaId)) {
            throw new DbMpaException("Рейтинга с id=" + mpaId + " нет");
        }
    }
}
