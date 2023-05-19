package ru.yandex.practicum.filmorate.model;

import lombok.*;
import ru.yandex.practicum.filmorate.validatorAnnotations.dateValidatorAnnotation.InMovieEpoch;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

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
}

