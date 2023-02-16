package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exeptions.NotFoundException;
import ru.yandex.practicum.filmorate.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "api/v1/films")
public class FilmController {
    private Map<Integer, Film> films = new HashMap<>();
    final static LocalDate GLOBAL_MOVIE_DAY = LocalDate.of(1895, 12, 28);
    int generatorId;

    @PostMapping
    public Film addFilm(@RequestBody Film film) {
        if (!isValid(film)) {
            throw new ValidationException("Данные фильма не соответствуют критериям");
        }

        film.setId(++generatorId);
        films.put(film.getId(), film);
        return film;
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) {
        if (!isValid(film)) {
            throw new ValidationException("Данные фильма не соответствуют критериям");
        }

        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
            return film;
        } else {
            throw new NotFoundException(String.format("Фильм %s не найден", film.getName()));
        }
    }

    @GetMapping
    public List<Film> getFilms() {
        return new ArrayList<>(films.values());
    }

    private boolean isValid(Film film) {
        if (film.getName().isBlank()) {
            return false;
        } else if (film.getDescription().length() > 200) {
            return false;
        } else if (film.getReleaseDate().isBefore(GLOBAL_MOVIE_DAY)) {
            return false;
        } else return !film.getDuration().isZero();

//            ("Название фильма не может быть пустым");
//            ("Максимальная длина описания фильма должна быть не больше 200 символов")
//            ("Дата релиза фильма должна быть не раньше 28 декабря 1895 года");
//            ("Продолжительность фильма должна быть положительной");
    }
}
