package ru.yandex.practicum.filmorate.validator;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
@Slf4j
public class FilmValidator {
    final static LocalDate GLOBAL_MOVIE_DAY = LocalDate.of(1895, 12, 28);
    final static int MIN_AMOUNT_OF_CHARACTERS = 200;

    public void validateFilm(Film film) {
        if (film.getName().isBlank()) {
            throw new ValidationException("Ошибка валидации, название фильма не может быть пустым");
        } else if (film.getDescription().length() > MIN_AMOUNT_OF_CHARACTERS) {
            throw new ValidationException("Ошибка валидации, максимальная длина описания фильма должна быть не больше 200 символов");
        } else if (film.getReleaseDate().isBefore(GLOBAL_MOVIE_DAY)) {
            throw new ValidationException("Ошибка валидации, дата релиза фильма должна быть не раньше 28 декабря 1895 года");
        } else if (film.getDuration() <= 0) {
            throw new ValidationException("Ошибка валидации, продолжительность фильма должна быть положительной");
        }
    }
}
