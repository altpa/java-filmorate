package ru.yandex.practicum.filmorate.model;

import lombok.Data;

@Data
public class Model {
    private int id;
    private String name;
    public Model(int id) {
        this.id = id;
    }
    public Model(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
