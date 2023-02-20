package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.repository.FilmRepository;
import ru.yandex.practicum.filmorate.validator.FilmValidator;

import java.util.List;

@RestController
@RequestMapping(value = "films")
@Slf4j
public class FilmController {
    FilmRepository filmRepository = new FilmRepository();
    FilmValidator filmValidator = new FilmValidator();

    @PostMapping
    public Film addFilm(@RequestBody Film film) {
        filmValidator.validateFilm(film);
        filmRepository.addFilmInRepository(film);
        log.debug("Фильм {} успешно добавлен", film.getName());
        return film;
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) {
        filmValidator.validateFilm(film);
        filmRepository.updateFilmInRepository(film);
        log.debug("Фильм {} успешно обновлен", film.getName());
        return film;
    }

    @GetMapping
    public List<Film> getFilms() {
        return filmRepository.getFilmsFromRepository();
    }
}
