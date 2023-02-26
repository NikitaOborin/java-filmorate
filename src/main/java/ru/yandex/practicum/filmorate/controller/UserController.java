package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.validator.UserValidator;

import java.util.List;

@RestController
@RequestMapping(value = "users")
@Slf4j
public class UserController {
    InMemoryUserStorage inMemoryUserStorage = new InMemoryUserStorage();
    UserValidator userValidator = new UserValidator();

    @PostMapping
    public User createUser(@RequestBody User user) {
        userValidator.validateUser(user);
        inMemoryUserStorage.create(user);
        log.debug("Пользователь {} успешно добавлен", user.getName());
        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        userValidator.validateUser(user);
        inMemoryUserStorage.update(user);
        log.debug("Пользователь {} успешно обновлен", user.getName());
        return user;
    }

    @GetMapping
    public List<User> getUsers() {
        return inMemoryUserStorage.getAll();
    }
}
