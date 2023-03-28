package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exeptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;
import java.util.stream.Collectors;

import static ru.yandex.practicum.filmorate.Util.emptyIfNull;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> films = new HashMap<>();
    private int generatorId;

    @Override
    public Film create(Film film) {
        film.setId(++generatorId);
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film update(Film film) {
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
        } else {
            throw new NotFoundException(String.format("Фильм %s не найден", film.getName()));
        }
        return film;
    }

    @Override
    public List<Film> getAll() {
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
    public Film deleteById(int id) {
        if (!films.containsKey(id)) {
            throw new NotFoundException(String.format("Фильм c id=%s не найден", id));
        }
        return films.remove(id);
    }

    @Override
    public List<Film> getBestFilms(int count) {
        return films.values().stream()
                .sorted((a, b) -> b.getLikes().size() - a.getLikes().size())
                .limit(count)
                .collect(Collectors.toList());
    }

    @Override
    public void addLike(int id, int userId) {
        Film f = getById(id);
        Set<Integer> likes = emptyIfNull(f.getLikes());
        likes.add(userId);
        f.setLikes(likes);
    }

    @Override
    public void removeLike(int filmId, int userId) {
        emptyIfNull(getById(generatorId).getLikes()).remove(userId);  // id !!!!!! ??????
    }
}
