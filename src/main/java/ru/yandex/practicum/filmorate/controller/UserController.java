package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.UserRepository;
import ru.yandex.practicum.filmorate.validator.UserValidator;

import java.util.List;

@RestController
@RequestMapping(value = "users")
@Slf4j
public class UserController {
    UserRepository userRepository = new UserRepository();
    UserValidator userValidator = new UserValidator();

    @PostMapping
    public User createUser(@RequestBody User user) {
        if (!userValidator.validateUser(user)) {
            throw new ValidationException("Ошибка валидации пользователя");
        }
        userRepository.addUserInRepository(user);
        log.debug("Пользователь {} успешно добавлен", user.getName());
        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        if (!userValidator.validateUser(user)) {
            throw new ValidationException("Ошибка валидации пользователя");
        }
        userRepository.updateUserInRepository(user);
        log.debug("Пользователь {} успешно обновлен", user.getName());
        return user;
    }

    @GetMapping
    public List<User> getUsers() {
        return userRepository.getUsersFromRepository();
    }
}
