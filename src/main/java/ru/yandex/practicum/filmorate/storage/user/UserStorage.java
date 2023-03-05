package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;

public interface UserStorage {
    void create(User user);

    void update(User user);

    ArrayList<User> getAll();

    User getById(long id);

    void deleteById(long id);
}
