package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exeptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryUserStorage implements UserStorage {
    private final Map<Integer, User> users = new HashMap<>();
    private int generatorId;

    @Override
    public void create(User user) {
        user.setId(++generatorId);
        users.put(user.getId(), user);
    }

    @Override
    public void update(User user) {
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
        } else {
            throw new NotFoundException(String.format("Пользователь %s не найден", user.getName()));
        }
    }

    @Override
    public ArrayList<User> getAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User getById(int id) {
        if (!users.containsKey(id)) {
            throw new NotFoundException(String.format("Пользователь c id=%s не найден", id));
        }
        return users.get(id);
    }

    @Override
    public void deleteById(int id) {
        if (!users.containsKey(id)) {
            throw new NotFoundException(String.format("Пользователь c id=%s не найден", id));
        }
        users.remove(id);
    }
}
