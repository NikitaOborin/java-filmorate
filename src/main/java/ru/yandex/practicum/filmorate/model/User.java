package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
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
@Builder
@UserName
public class User {
    @PositiveOrZero
    private long id;

    @NotBlank(message = "Ошибка валидации, электронная почта пользователя не может быть пустой")
    @Email(message = "Ошибка валидации, электронная почта пользователя должна содержать символ @")
    private final String email;

    @NotBlank(message = "Ошибка валидации, логин пользователя не может быть пустым")
    @UserLogin
    private final String login;

    private String name; // валидация имени - аннотация @UserName

    @PastOrPresent(message = "Ошибка валидации, дата рождения пользователя не может быть в будущем")
    private final LocalDate birthday;

    private final Set<Long> friends = new HashSet<>();
}
