package ru.yandex.practicum.filmorate.validator.user;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.FIELD) // Тип объекта над которым указывается аннотация
@Retention(RetentionPolicy.RUNTIME) // Тип хранения
@Constraint(validatedBy = UserLoginValidator.class)
@Documented
public @interface UserLogin {

    String message() default "Ошибка валидации, логин пользователя не может содержать пробелы";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
