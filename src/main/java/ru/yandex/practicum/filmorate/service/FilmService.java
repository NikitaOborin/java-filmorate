package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FilmService {
    @Autowired
    private final FilmStorage filmStorage;
    @Autowired
    private final UserStorage userStorage;

    public Film addFilm(Film film) {
        filmStorage.create(film);
        log.debug("Фильм {} успешно добавлен", film.getName());
        return film;
    }

    public Film updateFilm(Film film) {
        filmStorage.update(film);
        log.debug("Фильм {} успешно обновлен", film.getName());
        return film;
    }

    public List<Film> getFilms() {
        List<Film> films = filmStorage.getAll();
        log.debug("Список фильмов успешно получен");
        return films;
    }

    public Film getById(long id) {
        Film film = filmStorage.getById(id);
        log.debug("Фильм с id={} успешно получен", id);
        return film;
    }

    public void addLike(long filmId, long userId) {
        Film film = filmStorage.getById(filmId);
        User user = userStorage.getById(userId);
        film.getLikes().add(user.getId());
    }

    public void deleteLike(long filmId, long userId) {
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
