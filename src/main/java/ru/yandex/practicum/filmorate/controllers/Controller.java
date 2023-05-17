package ru.yandex.practicum.filmorate.controllers;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.HashMap;
import java.util.Map;

@RestController
public class Controller {

    private final Map<Integer, Object> data = new HashMap<>();

    public Controller() {
    }

    @PostMapping
    public <O extends Film> O create(@RequestBody O o) {
        data.put(o.getId(), o);
        return o;
    }

    @PutMapping
    public <O extends Film> O update(@RequestBody O o) {
        data.put(o.getId(), o);
        return o;
    }

    @GetMapping
    public Map<Integer, Object> get(){
        return data;
    }
}
