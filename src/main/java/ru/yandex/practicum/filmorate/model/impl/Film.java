package ru.yandex.practicum.filmorate.model.impl;

import lombok.*;
import ru.yandex.practicum.filmorate.model.Model;
import ru.yandex.practicum.filmorate.model.dateValidatorAnnotation.InMovieEpoch;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter @Setter
public class Film extends Model {
    @NotBlank(message = "Название фильма не может быть пустым")
    private final String name;
    @Size(max = 200)
    private final String description;
    @InMovieEpoch
    private final LocalDate releaseDate;
    @Positive(message = "Продолжительность фильма не может быть отрицательной")
    private final int duration;
    private final int rate;

    @Builder
    public Film(int id, String name, String description, LocalDate releaseDate, int duration, int rate){
        super(id);
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.rate = rate;
    }
}

