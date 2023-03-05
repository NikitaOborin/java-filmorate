package ru.yandex.practicum.filmorate.validator.user;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
@Slf4j
public class UserNameValidator implements ConstraintValidator<UserName, User> {

    @Override
    public boolean isValid(User user, ConstraintValidatorContext constraintValidatorContext) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
            log.debug("В результате валидации, в качестве имени пользователя был присвоен его логин");
        }
        return true;
    }
}
