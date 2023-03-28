package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exeptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static ru.yandex.practicum.filmorate.Util.emptyIfNull;

@Component
public class InMemoryUserStorage implements UserStorage {
    private final Map<Integer, User> users = new HashMap<>();
    private int generatorId;

    @Override
    public User create(User user) {
        user.setId(++generatorId);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public Optional<User> update(User user) {
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
        } else {
            throw new NotFoundException(String.format("Пользователь %s не найден", user.getName()));
        }
        return Optional.of(user);
    }

    @Override
    public List<User> getAll() {
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
    public User deleteById(int id) {
        if (!users.containsKey(id)) {
            throw new NotFoundException(String.format("Пользователь c id=%s не найден", id));
        }
        return users.remove(id);
    }

    @Override
    public boolean addFriend(int id, int friendId) {
        User u = users.get(id);
        if (u == null || users.get(friendId) == null) {
            throw new NotFoundException("Object not found");
        }
        Set<Integer> friends = emptyIfNull(u.getFriends());
        friends.add(friendId);
        u.setFriends(friends);
        return true;
    }

    @Override
    public boolean askFriend(int id, int friendId) {
        User u = users.get(id);
        if (u == null) {
            return false;
        }
        return emptyIfNull(u.getFriends()).contains(friendId);
    }

    @Override
    public boolean deleteFriend(int id, int friendId) {
        User u = users.get(id);
        if (u == null) {
            return false;
        }
        return emptyIfNull(u.getFriends()).remove(friendId);
    }

    @Override
    public List<User> findFriends(int id) {
        User u = users.get(id);
        if (u == null) {
            return emptyList();
        }
        return emptyIfNull(u.getFriends()).stream()
                .map(users::get)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> findCommonFriends(int id1, int id2) {
        User u1 = users.get(id1);
        User u2 = users.get(id2);
        if (u1 == null || u2 == null) {
            return emptyList();
        }
        Set<Integer> friends2 = emptyIfNull(u2.getFriends());
        return emptyIfNull(u1.getFriends()).stream()
                .filter(friends2::contains)
                .map(users::get)
                .collect(Collectors.toList());
    }
}
