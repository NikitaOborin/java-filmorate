package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.validator.FilmValidator;

import java.util.List;

@RestController
@RequestMapping(value = "films")
@Slf4j
public class FilmController {
    InMemoryFilmStorage inMemoryFilmStorage = new InMemoryFilmStorage();
    FilmValidator filmValidator = new FilmValidator();

    @PostMapping
    public Film addFilm(@RequestBody Film film) {
        filmValidator.validateFilm(film);
        inMemoryFilmStorage.create(film);
        log.debug("Фильм {} успешно добавлен", film.getName());
        return film;
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) {
        filmValidator.validateFilm(film);
        inMemoryFilmStorage.update(film);
        log.debug("Фильм {} успешно обновлен", film.getName());
        return film;
    }

    @GetMapping
    public List<Film> getFilms() {
        return inMemoryFilmStorage.getAll();
    }
}
