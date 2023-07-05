package ru.yandex.practicum.filmorate.controllers;

import lombok.RequiredArgsConstructor;
import lombok.val;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {
    private final FilmService filmService;

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        log.info("Получен POST запрос для добавления фильма: {}", film);
        val answer = filmService.addFilm(film);
        log.info("Фильм добавлен: {}", answer);
        return answer;
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        log.info("Получен PUT запрос для обновления фильма: {}", film);
        val answer = filmService.updateFilm(film);
        log.info("Данные обновлены для фильма: {}", answer);
        return answer;
    }

    @GetMapping
    public List<Film> getFilms() {
        log.info("Получен GET запрос для списка фильмов");
        val answer = filmService.getFilms();
        log.info("Отправлен список фильмов: {}", answer);
        return answer;
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable int id) {
        log.info("Получен GET запрос для поиска фильма с id:{}", id);
        val answer = filmService.getFilmById(id);
        log.info("Отправлен фильм: {}", answer);
        return answer;
    }

    @PutMapping("/{id}/like/{userId}")
    public Film setLike(@PathVariable int id, @PathVariable int userId) {
        log.info("Получен PUT запрос для добавления лайка id:{} у фильма id:{}", userId, id);
        val answer = filmService.setLike(id, userId);
        log.info("Фильму {} добавлен лайк", answer);
        return answer;
    }

    @DeleteMapping("/{id}/like/{userId}")
    public Film deleteLike(@PathVariable int id, @PathVariable int userId) {
        log.info("Получен DELETE запрос для удаления лайка id:{} у фильма id:{}", userId, id);
        val answer = filmService.deleteLike(id, userId);
        log.info("Фильму {} удален лайк", answer);
        return answer;
    }

    @GetMapping("/popular")
    public List<Film> getMostLiked(@RequestParam(required = false, defaultValue = "10") String count) {
        log.info("Получен GET запрос для получения списка первых {} самых залайканых фильмов", count);
        val answer = filmService.getMostLiked(count);
        log.info("Отправлен список фильмов: {}", answer);
        return answer;
    }
}