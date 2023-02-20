package ru.yandex.practicum.filmorate.repository;

import ru.yandex.practicum.filmorate.exeptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserRepository {
    private final Map<Integer, User> users = new HashMap<>();
    private int generatorId;

    public void addUserInRepository(User user) {
        user.setId(++generatorId);
        users.put(user.getId(), user);
    }

    public void updateUserInRepository(User user) {
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
        } else {
            throw new NotFoundException(String.format("Пользователь %s не найден", user.getName()));
        }
    }

    public ArrayList<User> getUsersFromRepository() {
        return new ArrayList<>(users.values());
    }
}
