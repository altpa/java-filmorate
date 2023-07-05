package ru.yandex.practicum.filmorate.model;

import lombok.*;
import ru.yandex.practicum.filmorate.validatorAnnotations.dateValidatorAnnotation.InMovieEpoch;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
public class Film {
    private int id;
    @NotBlank(message = "Название фильма не может быть пустым")
    private final String name;
    @Size(max = 200)
    private final String description;
    @InMovieEpoch
    private final LocalDate releaseDate;
    @Positive(message = "Продолжительность фильма не может быть отрицательной")
    private final int duration;
    private final int rate;
    private final Set<Integer> likes = new HashSet<>();
    private final Mpa mpa;
    private final Set<Genre> genres;
}

