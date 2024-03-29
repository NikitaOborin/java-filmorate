package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

public interface UserStorage {
    User create(User user);

    Optional<User> update(User user);

    List<User> getAll();

    User getById(int id);

    User deleteById(int id);

    boolean addFriend(int id, int friendId);

    boolean askFriend(int id, int friendId);

    boolean deleteFriend(int id, int friendId);

    List<User> findFriends(int id);

    List<User> findCommonFriends(int id1, int id2);
}
