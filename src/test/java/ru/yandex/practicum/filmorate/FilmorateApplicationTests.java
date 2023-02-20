package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exeptions.ValidationException;
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
		Film film = Film.builder()
				.name("filmName")
				.description("filmDescription")
				.releaseDate(LocalDate.of(2000, 1, 1))
				.duration(90)
				.build();
		assertDoesNotThrow(() -> filmValidator.validateFilm(film));
	}

	@Test
	public void shouldFailedValidationByFilmWithoutName() {
		Film film = Film.builder()
				.name("")
				.description("filmDescription")
				.releaseDate(LocalDate.of(2000, 1, 1))
				.duration(90)
				.build();
		assertThrows(ValidationException.class, () -> filmValidator.validateFilm(film));
	}

	@Test
	public void shouldSuccessValidationBySizeFilmDescription200() {
		Film film = Film.builder()
				.name("filmName")
				.description("Lorem ipsum dolor sit amet, consectetuer adipiscing elit, " +
						     "sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. " +
						     "Ut wisi enim ad minim veniam, quis nostrud exerci tatio")
				.releaseDate(LocalDate.of(2000, 1, 1))
				.duration(90)
				.build();
		assertDoesNotThrow(() -> filmValidator.validateFilm(film));
	}

	@Test
	public void shouldFailedValidationBySizeFilmDescription201() {
		Film film = Film.builder()
				.name("filmName")
				.description("Lorem ipsum dolor sit amet, consectetuer adipiscing elit, " +
						"sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. " +
						"Ut wisi enim ad minim veniam, quis nostrud exerci tatio1")
				.releaseDate(LocalDate.of(2000, 1, 1))
				.duration(90)
				.build();
		assertThrows(ValidationException.class, () -> filmValidator.validateFilm(film));
	}

	@Test
	public void shouldSuccessValidationByFilmReleaseDate18951228() {
		Film film = Film.builder()
				.name("filmName")
				.description("filmDescription")
				.releaseDate(LocalDate.of(1895, 12, 28))
				.duration(90)
				.build();
		assertDoesNotThrow(() -> filmValidator.validateFilm(film));
	}

	@Test
	public void shouldFailedValidationByFilmReleaseDate18951229() {
		Film film = Film.builder()
				.name("filmName")
				.description("filmDescription")
				.releaseDate(LocalDate.of(1895, 12, 27))
				.duration(90)
				.build();
		assertThrows(ValidationException.class, () -> filmValidator.validateFilm(film));
	}

	@Test
	public void shouldSuccessValidationByFilmDuration1() {
		Film film = Film.builder()
				.name("filmName")
				.description("filmDescription")
				.releaseDate(LocalDate.of(2000, 1, 1))
				.duration(1)
				.build();
		assertDoesNotThrow(() -> filmValidator.validateFilm(film));
	}

	@Test
	public void shouldFailedValidationByFilmDuration0() {
		Film film = Film.builder()
				.name("filmName")
				.description("filmDescription")
				.releaseDate(LocalDate.of(2000, 1, 1))
				.duration(0)
				.build();
		assertThrows(ValidationException.class, () -> filmValidator.validateFilm(film));
	}

	@Test
	public void shouldSuccessValidationByCorrectUser() {
		User user = User.builder()
				.login("userLogin")
				.email("userEmail@ya.ru")
				.name("UserName")
				.birthday(LocalDate.of(2000, 1, 1))
				.build();
		assertDoesNotThrow(() -> userValidator.validateUser(user));
	}

	@Test
	public void shouldFailedValidationByBlankUserEmail() {
		User user = User.builder()
				.login("userLogin")
				.email("")
				.name("UserName")
				.birthday(LocalDate.of(2000, 1, 1))
				.build();
		assertThrows(ValidationException.class, () -> userValidator.validateUser(user));
	}

	@Test
	public void shouldFailedValidationByUserEmailWithoutEmailSymbol() {
		User user = User.builder()
				.login("userLogin")
				.email("userEmail.ya.ru")
				.name("UserName")
				.birthday(LocalDate.of(2000, 1, 1))
				.build();
		assertThrows(ValidationException.class, () -> userValidator.validateUser(user));
	}

	@Test
	public void shouldFailedValidationByEmptyUserLogin() {
		User user = User.builder()
				.login("")
				.email("userEmail@ya.ru")
				.name("UserName")
				.birthday(LocalDate.of(2000, 1, 1))
				.build();
		assertThrows(ValidationException.class, () -> userValidator.validateUser(user));
	}

	@Test
	public void shouldFailedValidationByUserLoginWithSpace() {
		User user = User.builder()
				.login("user Login")
				.email("userEmail@ya.ru")
				.name("UserName")
				.birthday(LocalDate.of(2000, 1, 1))
				.build();
		assertThrows(ValidationException.class, () -> userValidator.validateUser(user));
	}

	@Test
	public void shouldSuccessValidationByEmptyUserName() {
		User user = User.builder()
				.login("userLogin")
				.email("userEmail@ya.ru")
				.name("")
				.birthday(LocalDate.of(2000, 1, 1))
				.build();
		assertDoesNotThrow(() -> userValidator.validateUser(user));
	}

	@Test
	public void shouldEqualsLoginAndNameByEmptyUserName() {
		User user = User.builder()
				.login("userLogin")
				.email("userEmail@ya.ru")
				.name("")
				.birthday(LocalDate.of(2000, 1, 1))
				.build();
		userValidator.validateUser(user);
		assertEquals(user.getName(), user.getLogin());
	}

	@Test
	public void shouldSuccessValidationByUserBirthdayNow() {
		User user = User.builder()
				.login("userLogin")
				.email("userEmail@ya.ru")
				.name("UserName")
				.birthday(LocalDate.now())
				.build();
		assertDoesNotThrow(() -> userValidator.validateUser(user));
	}

	@Test
	public void shouldFailedValidationByUserBirthdayInFuture() {
		User user = User.builder()
				.login("userLogin")
				.email("userEmail@ya.ru")
				.name("UserName")
				.birthday(LocalDate.of(2024, 1, 1))
				.build();
		assertThrows(ValidationException.class, () -> userValidator.validateUser(user));
	}
}
