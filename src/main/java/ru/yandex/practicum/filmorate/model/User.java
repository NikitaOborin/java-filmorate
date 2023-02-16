package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class User {
    private int id;
    private final String email;
    private final String login;
    private String name;
    private final LocalDate birthday;

    public User(String login, String email, String name, LocalDate birthday) {
        this.login = login;
        this.email = email;
        this.name = name;
        this.birthday = birthday;
    }
}
