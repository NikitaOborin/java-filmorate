package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {
    Film create(Film film);

    Film update(Film film);

    List<Film> getAll();

    Film getById(int id);

    Film deleteById(int id);

    List<Film> getBestFilms(int count);

    void addLike(int id, int userId);

    void removeLike(int filmId, int userId);
}
