package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import ru.yandex.practicum.filmorate.validator.UserValidator;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserStorage userStorage;
    private final UserValidator userValidator;

    public User createUser(User user) {
        userValidator.validateUser(user);
        userStorage.create(user);
        log.debug("Пользователь {} успешно добавлен", user.getName());
        return user;
    }

    public User updateUser(User user) {
        userValidator.validateUser(user);
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
        log.debug("Пользователь с id={} успешно получен", id);
        return user;
    }

    public void addFriendship(int userId, int friendId) {
        if (userId == friendId) {
            throw new ValidationException("Пользователь не может добавить в друзья сам себя");
        }
        User user = userStorage.getById(userId);
        User newFriend = userStorage.getById(friendId);
        user.getFriends().add(friendId);
        newFriend.getFriends().add(userId);
    }

    public void deleteFriendship(int userId, int friendId) {
        User user = userStorage.getById(userId);
        User newFriend = userStorage.getById(friendId);

        if (!user.getFriends().contains(friendId) || !newFriend.getFriends().contains(userId)) {
            throw new ValidationException("Пользователи не являются друзьями");
        }

        user.getFriends().remove(friendId);
        newFriend.getFriends().remove(userId);
    }

    public List<User> getFriendshipById(int id) {
        User user = userStorage.getById(id);
        return user.getFriends().stream()
                .map(userStorage::getById)
                .collect(Collectors.toList());
    }

    public List<User> getMutualFriendship(int userId, int friendId) {
        User user = userStorage.getById(userId);
        User otherUser = userStorage.getById(friendId);

        return user.getFriends().stream()
                .filter(u -> otherUser.getFriends().contains(u))
                .map(userStorage::getById)
                .collect(Collectors.toList());
    }
}
