package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import ru.yandex.practicum.filmorate.validator.FilmValidator;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private final FilmValidator filmValidator;

    public Film addFilm(Film film) {
        filmValidator.validateFilm(film);
        filmStorage.create(film);
        log.debug("Фильм {} успешно добавлен", film.getName());
        return film;
    }

    public Film updateFilm(Film film) {
        filmValidator.validateFilm(film);
        filmStorage.update(film);
        log.debug("Фильм {} успешно обновлен", film.getName());
        return film;
    }

    public List<Film> getFilms() {
        List<Film> films = filmStorage.getAll();
        log.debug("Список фильмов успешно получен");
        return films;
    }

    public Film getById(int id) {
        Film film = filmStorage.getById(id);
        log.debug("Фильм с id={} успешно получен", id);
        return film;
    }

    public void addLike(int filmId, int userId) {
        User user = userStorage.getById(userId);
        Film film = filmStorage.getById(filmId);
        film.getLikes().add(user.getId());
    }

    public void deleteLike(int filmId, int userId) {
        Film film = filmStorage.getById(filmId);
        if (film.getLikes().contains(userId)) {
            film.getLikes().remove(userId);
            filmStorage.update(film);
        } else {
            throw new NotFoundException(String.format("Лайк на фильм id=%s от пользователя с id=%s не найден", filmId, userId));
        }
    }

    public List<Film> getBestFilms(int count) {
        return filmStorage.getBestFilms(count);
    }
}
