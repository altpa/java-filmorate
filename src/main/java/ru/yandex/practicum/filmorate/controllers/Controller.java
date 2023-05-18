package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Model;

import java.util.*;

@RestController
@Slf4j
public class Controller {
    private final Map<Integer, Model> data = new HashMap<>();
    private int idCounter = 1;

    public void add(Model model) {
        if (model.getId() == 0) {
            log.info("POST запрос");
            model.setId(idCounter);
            data.put(idCounter, model);
            log.info("Добавлен {} id{}", model.getClass().getSimpleName(), model.getId());
            idCounter++;
            return;
        }
        if (data.containsKey(model.getId())) {
            log.info("PUT запрос");
            data.put(model.getId(), model);
            log.info("Обновлен {} id{}", model.getClass().getSimpleName(), model.getId());
        } else {
            throw new ValidationException("Такого id нет. id=" + model.getId());
        }
    }

    @GetMapping
    public List<Model> get(){
        log.info("Получен GET запрос");
        return new ArrayList<>(data.values());
    }
}