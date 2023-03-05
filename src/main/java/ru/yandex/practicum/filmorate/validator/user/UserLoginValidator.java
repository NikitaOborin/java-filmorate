package ru.yandex.practicum.filmorate.validator.user;


import ru.yandex.practicum.filmorate.model.User;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
public class UserLoginValidator implements ConstraintValidator<UserLogin, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return !s.contains(" ");
    }
}
