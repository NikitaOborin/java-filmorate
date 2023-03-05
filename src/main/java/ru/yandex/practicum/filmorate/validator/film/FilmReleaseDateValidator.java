package ru.yandex.practicum.filmorate.validator.film;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class FilmReleaseDateValidator implements ConstraintValidator<FilmReleaseDate, LocalDate> {
    private static final LocalDate GLOBAL_MOVIE_DAY = LocalDate.of(1895, 12, 28);

    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {
        return localDate.isAfter(GLOBAL_MOVIE_DAY) || localDate.isEqual(GLOBAL_MOVIE_DAY);
    }
}
