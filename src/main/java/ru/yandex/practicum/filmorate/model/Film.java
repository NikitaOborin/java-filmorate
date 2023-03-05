package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.validator.film.FilmReleaseDate;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
public class Film {
    @PositiveOrZero
    private long id;

    @NotBlank(message = "Ошибка валидации, название фильма не может быть пустым")
    private final String name;

    @NotNull(message = "Ошибка валидации, отсутствует описание фильма")
    @Size(min = 1, max = 200, message = "Ошибка валидации, максимальная длина описания фильма должна быть не больше 200 символов")
    private final String description;

    @NotNull(message = "Отсутствует дата релиза фильма фильма")
    @FilmReleaseDate
    private final LocalDate releaseDate;

    @Min(value = 1, message = "Ошибка валидации, продолжительность фильма должна быть положительной")
    private final int duration;

    private final Set<Long> likes = new HashSet<>();
}
