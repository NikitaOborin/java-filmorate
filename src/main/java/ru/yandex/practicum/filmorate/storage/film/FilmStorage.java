package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.List;

public interface FilmStorage {
    void create(Film film);
    void update(Film film);
    ArrayList<Film> getAll();
    Film getById(int id);
    void deleteById(int id);
    List<Film> getBestFilms(int count);
}
