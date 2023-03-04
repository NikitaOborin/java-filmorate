package ru.yandex.practicum.filmorate.validator.film;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.FIELD) // Тип объекта над которым указывается аннотация
@Retention(RetentionPolicy.RUNTIME) // Тип хранения
@Constraint(validatedBy = FilmReleaseDateValidator.class)
@Documented
public @interface FilmReleaseDate {

    String message() default "Ошибка валидации, дата релиза фильма должна быть не раньше 28 декабря 1895 года";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
