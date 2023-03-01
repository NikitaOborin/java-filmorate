package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exeptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Override
    public Film getById(int id) {
        if (!films.containsKey(id)) {
            throw new NotFoundException(String.format("Фильм c id=%s не найден", id));
        }
        return films.get(id);
    }

    @Override
    public void deleteById(int id) {
        if (!films.containsKey(id)) {
            throw new NotFoundException(String.format("Фильм c id=%s не найден", id));
        }
        films.remove(id);
    }

    @Override
    public List<Film> getBestFilms(int count) {
        return films.values().stream()
                .sorted((a, b) -> b.getLikes().size() - a.getLikes().size())
                .limit(count)
                .collect(Collectors.toList());
    }
}
