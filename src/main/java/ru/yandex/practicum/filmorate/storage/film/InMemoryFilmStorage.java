package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exeptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> films = new HashMap<>();
    private int generatorId;

    @Override
    public void create(Film film) {
        film.setId(++generatorId);
        films.put(film.getId(), film);
    }

    @Override
    public void update(Film film) {
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
        } else {
            throw new NotFoundException(String.format("Фильм %s не найден", film.getName()));
        }
    }

    @Override
    public ArrayList<Film> getAll() {
        return new ArrayList<>(films.values());
    }
}
