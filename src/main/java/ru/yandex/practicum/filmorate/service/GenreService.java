package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.film.GenreStorage;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
public class GenreService {
    private final GenreStorage genreStorage;

    public GenreService(GenreStorage genreStorage) {
        this.genreStorage = genreStorage;
    }

    public Genre getGenreById(int id) {
        Genre g = genreStorage.findById(id);
        if (g == null) {
            throw new NotFoundException("Жанр с идентификатором " + id + " не найден.");
        }
        return g;
    }

    public Collection<Genre> getAll() {
        return genreStorage.findAll();
    }

    public Map<Integer, List<Genre>> getFilmGenres() {
        return genreStorage.getFilmGenres();
    }

    public Map<Integer, List<Genre>> getFilmGenresForPopular(Integer count) {
        return genreStorage.getFilmGenresForPopular(count);
    }
}
