package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validator.FilmValidator;
import ru.yandex.practicum.filmorate.validator.UserValidator;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FilmorateApplicationTests {
	UserValidator userValidator = new UserValidator();
	FilmValidator filmValidator = new FilmValidator();

	@Test
	public void shouldSuccessValidationByCorrectFilm() {
		Film film = new Film("filmName", "filmDescription",
				LocalDate.of(2000, 1, 1), 90);
		assertTrue(filmValidator.validateFilm(film));
	}

	@Test
	public void shouldFailedValidationByFilmWithoutName() {
		Film film = new Film("", "filmDescription",
				LocalDate.of(2000, 1, 1), 90);
		assertFalse(filmValidator.validateFilm(film));
	}

	@Test
	public void shouldSuccessValidationBySizeFilmDescription200() {
		Film film = new Film("filmName", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit, " +
				"sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. Ut wisi enim " +
				"ad minim veniam, quis nostrud exerci tatio",
				LocalDate.of(2000, 1, 1), 90);
		assertTrue(filmValidator.validateFilm(film));
	}

	@Test
	public void shouldFailedValidationBySizeFilmDescription201() {
		Film film = new Film("filmName", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit, " +
				"sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. Ut wisi enim " +
				"ad minim veniam, quis nostrud exerci tatio1",
				LocalDate.of(2000, 1, 1), 90);
		assertFalse(filmValidator.validateFilm(film));
	}

	@Test
	public void shouldSuccessValidationByFilmReleaseDate18951228() {
		Film film = new Film("filmName", "filmDescription",
				LocalDate.of(1895, 12, 28), 90);
		assertTrue(filmValidator.validateFilm(film));
	}

	@Test
	public void shouldFailedValidationByFilmReleaseDate18951229() {
		Film film = new Film("filmName", "filmDescription",
				LocalDate.of(1895, 12, 27), 90);
		assertFalse(filmValidator.validateFilm(film));
	}

	@Test
	public void shouldSuccessValidationByFilmDuration1() {
		Film film = new Film("filmName", "filmDescription",
				LocalDate.of(2000, 1, 1), 1);
		assertTrue(filmValidator.validateFilm(film));
	}

	@Test
	public void shouldFailedValidationByFilmDuration0() {
		Film film = new Film("filmName", "filmDescription",
				LocalDate.of(2000, 1, 1), 0);
		assertFalse(filmValidator.validateFilm(film));
	}

	@Test
	public void shouldSuccessValidationByCorrectUser() {
		User user = new User("userLogin", "userEmail@ya.ru","UserName",
				LocalDate.of(2000, 1, 1));
		assertTrue(userValidator.validateUser(user));
	}

	@Test
	public void shouldFailedValidationByBlankUserEmail() {
		User user = new User("userLogin", "", "UserName",
				LocalDate.of(2000, 1, 1));
		assertFalse(userValidator.validateUser(user));
	}

	@Test
	public void shouldFailedValidationByUserEmailWithoutEmailSymbol() {
		User user = new User("userLogin", "userEmail.ya.ru", "UserName",
				LocalDate.of(2000, 1, 1));
		assertFalse(userValidator.validateUser(user));
	}

	@Test
	public void shouldFailedValidationByEmptyUserLogin() {
		User user = new User("", "userEmail@ya.ru","UserName",
				LocalDate.of(2000, 1, 1));
		assertFalse(userValidator.validateUser(user));
	}

	@Test
	public void shouldFailedValidationByUserLoginWithSpace() {
		User user = new User("user Login", "userEmail@ya.ru", "UserName",
				LocalDate.of(2000, 1, 1));
		assertFalse(userValidator.validateUser(user));
	}

	@Test
	public void shouldSuccessValidationByEmptyUserName() {
		User user = new User("userLogin", "userEmail@ya.ru", "",
				LocalDate.of(2000, 1, 1));
		assertTrue(userValidator.validateUser(user));
	}

	@Test
	public void shouldEqualsLoginAndNameByEmptyUserName() {
		User user = new User("userLogin", "userEmail@ya.ru", "",
				LocalDate.of(2000, 1, 1));
		userValidator.validateUser(user);
		assertEquals(user.getName(), user.getLogin());
	}

	@Test
	public void shouldSuccessValidationByUserBirthdayNow() {
		User user = new User("userLogin", "userEmail@ya.ru", "UserName",
				LocalDate.now());
		assertTrue(userValidator.validateUser(user));
	}

	@Test
	public void shouldFailedValidationByUserBirthdayInFuture() {
		User user = new User("userLogin", "userEmail@ya.ru", "UserName",
				LocalDate.of(2024, 1, 1));
		assertFalse(userValidator.validateUser(user));
	}
}
