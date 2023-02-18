package ru.yandex.practicum.filmorate.validator;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
@Slf4j
public class FilmValidator {
    final static LocalDate GLOBAL_MOVIE_DAY = LocalDate.of(1895, 12, 28);
    final static int MIN_AMOUNT_OF_CHARACTERS = 200;

    public boolean validateFilm(Film film) {
        if (film.getName().isBlank()) {
            log.debug("Ошибка валидации, название фильма не может быть пустым");
            return false;
        } else if (film.getDescription().length() > MIN_AMOUNT_OF_CHARACTERS) {
            log.debug("Ошибка валидации, максимальная длина описания фильма должна быть не больше 200 символов");
            return false;
        } else if (film.getReleaseDate().isBefore(GLOBAL_MOVIE_DAY)) {
            log.debug("Ошибка валидации, дата релиза фильма должна быть не раньше 28 декабря 1895 года");
            return false;
        } else if (film.getDuration() <= 0) {
            log.debug("Ошибка валидации, продолжительность фильма должна быть положительной");
            return false;
        } else {
            return true;
        }
    }
}
