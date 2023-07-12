package ru.yandex.practicum.filmorate.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.MpaService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/mpa")
@RequiredArgsConstructor
public class MpaController {
    private final MpaService mpaService;

    @GetMapping
    public List<Mpa> getRatings() {
        log.info("Получен GET запрос для списка рейтингов MPA");
        val answer = mpaService.getRatings();
        log.info("Отправлен список рейтингов MPA: {}", answer);
        return answer;
    }

    @GetMapping("/{id}")
    public Mpa getRatingById(@PathVariable int id) {
        log.info("Получен GET запрос для рейтинга по id={}", id);
        val answer = mpaService.getRatingById(id);
        log.info("Отправлен рейтинг MPA: {}", answer);
        return answer;
    }
}
