package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;

@Service
@Slf4j
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    @Autowired
    public FilmService(@Qualifier("filmDbStorage") FilmStorage filmStorage, @Qualifier("userDbStorage") UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public Film addFilm(Film film) {
        filmStorage.create(film);
        log.debug("Фильм {} успешно добавлен", film.getName());
        return film;
    }

    public Film updateFilm(Film film) {
        Film updatedFilm = filmStorage.update(film);
        if (updatedFilm == null) {
            throw new NotFoundException("Фильм с id=" + film.getId() + " не найден");
        }
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
        if (film == null) {
            throw new NotFoundException("Фильм с id=" + id + " не найден");
        }
        log.debug("Фильм с id={} успешно получен", id);
        return film;
    }

    public Film deleteById(int id) {
        return filmStorage.deleteById(id);
    }

    public void addLike(int filmId, int userId) {
        filmStorage.addLike(filmId, userId);
    }

    public void deleteLike(int filmId, int userId) {
        Film film = filmStorage.getById(filmId);
        if (film.getLikes().contains(userId)) {
            filmStorage.removeLike(filmId, userId);
            filmStorage.update(film);
        } else {
            throw new NotFoundException(String.format("Лайк на фильм id=%s от пользователя с id=%s не найден", filmId, userId));
        }
    }

    public List<Film> getBestFilms(int count) {
        return filmStorage.getBestFilms(count);
    }
}
