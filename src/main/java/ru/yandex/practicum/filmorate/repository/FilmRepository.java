package ru.yandex.practicum.filmorate.repository;

import ru.yandex.practicum.filmorate.exeptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FilmRepository {
    private final Map<Integer, Film> films = new HashMap<>();
    private int generatorId;

    public void addFilmInRepository(Film film) {
        film.setId(++generatorId);
        films.put(film.getId(), film);
    }

    public void updateFilmInRepository(Film film) {
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
        } else {
            throw new NotFoundException(String.format("Фильм %s не найден", film.getName()));
        }
    }

    public ArrayList<Film> getFilmsFromRepository() {
        return new ArrayList<>(films.values());
    }
}
