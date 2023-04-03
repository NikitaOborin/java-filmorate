package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;

@Service
@Slf4j
public class UserService {

    private UserStorage userStorage;

    @Autowired
    public UserService(@Qualifier("userDbStorage") UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User createUser(User user) {
        userStorage.create(user);
        log.debug("Пользователь {} успешно добавлен", user.getName());
        return user;
    }

    public User updateUser(User user) {   //!!!!!!!!!!!!!!!!!
        int id = user.getId();
        if (userStorage.getById(id) == null) {
            throw new NotFoundException("User с id=" + id + " не найден");
        }
        userStorage.update(user);
        log.debug("Пользователь {} успешно обновлен", user.getName());
        return user;
    }

    public List<User> getUsers() {
        List<User> users = userStorage.getAll();
        log.debug("Список пользователей успешно получен");
        return users;
    }

    public User getById(int id) {
        User user = userStorage.getById(id);
        if (user == null) {
            throw new NotFoundException("User с id=" + id + " не найден");
        }
        log.debug("Пользователь с id={} успешно получен", id);
        return user;
    }

    public User deleteById(int id) {
        return userStorage.deleteById(id);
    }

    public Boolean addFriendship(int id, int friendId) {
        return userStorage.addFriend(id, friendId);
    }

    public Boolean deleteFriendship(int id, int friendId) {   // здесь должен быть NotFoundEx
        return userStorage.deleteFriend(id, friendId);
    }

    public List<User> getFriendshipById(int id) {
        return userStorage.findFriends(id);
    }

    public List<User> getMutualFriendship(int userId, int friendId) {
        return userStorage.findCommonFriends(userId, friendId);
    }
}
