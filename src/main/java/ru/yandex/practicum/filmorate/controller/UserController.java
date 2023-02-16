package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exeptions.NotFoundException;
import ru.yandex.practicum.filmorate.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "api/v1/users")
public class UserController {
    private Map<Integer, User> users = new HashMap<>();
    int generatorId;

    @PostMapping
    public User createUser(@RequestBody User user) {
        if (!isValid(user)) {
            throw new ValidationException("Данные пользователя не соответствуют критериям");
        }

        user.setId(++generatorId);
        users.put(user.getId(), user);
        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        if (!isValid(user)) {
            throw new ValidationException("Данные пользователя не соответствуют критериям");
        }

        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            return user;
        } else {
            throw new NotFoundException(String.format("Пользователь %s не найден", user.getName()));
        }
    }

    @GetMapping
    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }

    private boolean isValid(User user) {
        if (user.getEmail().isBlank() && user.getEmail().contains("@")) {
            return false;
        } else if (user.getLogin().isBlank()) {
            return false;
        } else if (user.getName().isBlank()) {
            user.setName(user.getLogin());
            return true;
        } else return !user.getBirthday().isAfter(LocalDate.now());
    }
}
