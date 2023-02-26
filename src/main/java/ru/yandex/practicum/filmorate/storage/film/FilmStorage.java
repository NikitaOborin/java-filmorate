package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;

public interface FilmStorage {
    void create(Film film);
    void update(Film film);
    ArrayList<Film> getAll();
}
