package ru.yandex.practicum.filmorate.model;

import lombok.*;
import ru.yandex.practicum.filmorate.validator.user.UserLogin;
import ru.yandex.practicum.filmorate.validator.user.UserName;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@UserName
public class User {
    @PositiveOrZero
    private int id;

    @NotBlank(message = "Ошибка валидации, электронная почта пользователя не может быть пустой")
    @Email(message = "Ошибка валидации, электронная почта пользователя должна содержать символ @")
    private String email;

    @NotBlank(message = "Ошибка валидации, логин пользователя не может быть пустым")
    @UserLogin
    private String login;

    private String name; // валидация имени - аннотация @UserName

    @PastOrPresent(message = "Ошибка валидации, дата рождения пользователя не может быть в будущем")
    private LocalDate birthday;

    private Set<Integer> friends = new HashSet<>();

    public User(int id, @NonNull String email, @NonNull String login, String name, @NonNull LocalDate birthday) {
        this.id = id;
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
    }
}
