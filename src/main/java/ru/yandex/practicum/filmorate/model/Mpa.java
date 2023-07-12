package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Mpa {
    private final int id;
    private final String name;

    public String toString() {
        return "{id: " + this.getId() + ". name: " + this.getName() + "}";
    }
}
