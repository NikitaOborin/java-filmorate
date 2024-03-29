package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "users")
@Validated
public class UserController {
    @Autowired
    private final UserService userService;

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        return userService.updateUser(user);
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriendship(@PathVariable int id, @PathVariable int friendId) {
        userService.addFriendship(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriendship(@PathVariable int id, @PathVariable int friendId) {
        userService.deleteFriendship(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> getFriendshipById(@PathVariable int id) {
        return userService.getFriendshipById(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getMutualFriendship(@PathVariable int id, @PathVariable int otherId) {
        return userService.getMutualFriendship(id, otherId);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id) {
        return userService.getById(id);
    }

    @DeleteMapping("/{id}")
    public User deleteById(@PathVariable int id) {
        return userService.deleteById(id);
    }
}
