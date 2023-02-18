package ru.yandex.practicum.filmorate.validator;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
@Slf4j
public class UserValidator {
    public boolean validateUser(User user) {
        if (user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            log.debug("Ошибка валидации, электронная почта пользователя не может быть пустой и должна содержать символ @");
            return false;
        } else if (user.getLogin().isBlank() || user.getLogin().contains(" ")) {
            log.debug("Ошибка валидации, логин пользователя не может быть пустым и содержать пробелы");
            return false;
        } else if (user.getBirthday().isAfter(LocalDate.now())) {
            log.debug("Ошибка валидации, дата рождения пользователя не может быть в будущем");
            return false;
        } else if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
            log.debug("В результате валидации, в качестве имени пользователя был присвоен его логин");
            return true;
        } else {
            return true;
        }
    }
}
