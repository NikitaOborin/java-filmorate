package ru.yandex.practicum.filmorate.validator.user;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.TYPE) // Тип объекта над которым указывается аннотация
@Retention(RetentionPolicy.RUNTIME) // Тип хранения
@Constraint(validatedBy = UserNameValidator.class)
@Documented
public @interface UserName {

    String message() default " ";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
