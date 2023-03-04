package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exeptions.NotFoundException;
import ru.yandex.practicum.filmorate.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class FilmorateApplicationTests {
	private FilmService filmService;
	private UserService userService;

	@BeforeEach
	public void servicesInitialisation() {
		InMemoryFilmStorage filmStorage = new InMemoryFilmStorage();
		InMemoryUserStorage userStorage = new InMemoryUserStorage();

		filmService = new FilmService(filmStorage, userStorage);
		userService = new UserService(userStorage);
	}

	private static final Validator validator;
    static {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.usingContext().getValidator();
    }

	// тесты для FilmService
	@Test
	public void shouldSuccessAddFilm() {
		Film film = Film.builder()
				.name("filmName")
				.description("filmDescription")
				.releaseDate(LocalDate.of(2000, 1, 1))
				.duration(90)
				.build();
		filmService.addFilm(film);

		assertEquals(filmService.getById(1), film);
	}

	@Test
	public void shouldSuccessUpdateFilm() {
		Film film = Film.builder()
				.name("filmName")
				.description("filmDescription")
				.releaseDate(LocalDate.of(2000, 1, 1))
				.duration(90)
				.build();
		filmService.addFilm(film);

		film = Film.builder()
				.name("updateFilmName")
				.description("updateFilmDescription")
				.releaseDate(LocalDate.of(2001, 1, 1))
				.duration(91)
				.build();
		film.setId(1);
		filmService.updateFilm(film);

		assertEquals(filmService.getById(1).getName(), film.getName());
	}

	@Test
	public void shouldGet1FilmByAdd1Film() {
		Film film = Film.builder()
				.name("filmName")
				.description("filmDescription")
				.releaseDate(LocalDate.of(2000, 1, 1))
				.duration(90)
				.build();
		filmService.addFilm(film);

		assertEquals(filmService.getFilms().size(), 1);
	}

	@Test
	public void shouldGetFilmWithId1ById1() {
		Film film = Film.builder()
				.name("filmName")
				.description("filmDescription")
				.releaseDate(LocalDate.of(2000, 1, 1))
				.duration(90)
				.build();
		filmService.addFilm(film);

		assertEquals(filmService.getById(1), film);
	}

	@Test
	public void shouldSuccessAddLike() {
		Film film = Film.builder()
				.name("filmName")
				.description("filmDescription")
				.releaseDate(LocalDate.of(2000, 1, 1))
				.duration(90)
				.build();
		filmService.addFilm(film);

		User user = User.builder()
				.login("userLogin")
				.email("userEmail@ya.ru")
				.name("UserName")
				.birthday(LocalDate.of(2000, 1, 1))
				.build();
		userService.createUser(user);

		filmService.addLike(1, 1);
		assertEquals(filmService.getById(1).getLikes().size(), 1);
	}

	@Test
	public void shouldSuccessDeleteLike() {
		Film film = Film.builder()
				.name("filmName")
				.description("filmDescription")
				.releaseDate(LocalDate.of(2000, 1, 1))
				.duration(90)
				.build();
		filmService.addFilm(film);

		User user = User.builder()
				.login("userLogin")
				.email("userEmail@ya.ru")
				.name("UserName")
				.birthday(LocalDate.of(2000, 1, 1))
				.build();
		userService.createUser(user);

		filmService.addLike(1, 1);
		filmService.deleteLike(1, 1);
		assertEquals(filmService.getById(1).getLikes().size(), 0);
	}

	@Test
	public void shouldFailedDeleteLike() {
		Film film = Film.builder()
				.name("filmName")
				.description("filmDescription")
				.releaseDate(LocalDate.of(2000, 1, 1))
				.duration(90)
				.build();
		filmService.addFilm(film);

		User user = User.builder()
				.login("userLogin")
				.email("userEmail@ya.ru")
				.name("UserName")
				.birthday(LocalDate.of(2000, 1, 1))
				.build();
		userService.createUser(user);

		assertThrows(NotFoundException.class, () -> filmService.deleteLike(1, 1));
	}

	@Test
	public void shouldSuccessGetBestFilms() {
		Film film = Film.builder()
				.name("filmName")
				.description("filmDescription")
				.releaseDate(LocalDate.of(2000, 1, 1))
				.duration(90)
				.build();
		filmService.addFilm(film);

		Film film2 = Film.builder()
				.name("filmName2")
				.description("filmDescription2")
				.releaseDate(LocalDate.of(2002, 1, 1))
				.duration(92)
				.build();
		filmService.addFilm(film2);

		User user = User.builder()
				.login("userLogin")
				.email("userEmail@ya.ru")
				.name("UserName")
				.birthday(LocalDate.of(2000, 1, 1))
				.build();
		userService.createUser(user);

		filmService.addLike(1, 1);
		assertEquals(filmService.getBestFilms(10).get(0).getName(), film.getName());
	}

	// тесты для UserService
	@Test
	public void shouldSuccessCreateUser() {
		User user = User.builder()
				.login("userLogin")
				.email("userEmail@ya.ru")
				.name("UserName")
				.birthday(LocalDate.of(2000, 1, 1))
				.build();
		userService.createUser(user);

		assertEquals(userService.getById(1), user);
	}

	@Test
	public void shouldSuccessUpdateUser() {
		User user = User.builder()
				.login("userLogin")
				.email("userEmail@ya.ru")
				.name("UserName")
				.birthday(LocalDate.of(2000, 1, 1))
				.build();
		userService.createUser(user);

		user = User.builder()
				.login("newUserLogin")
				.email("newUserEmail@ya.ru")
				.name("newUserName")
				.birthday(LocalDate.of(2001, 1, 1))
				.build();
		user.setId(1);
		userService.updateUser(user);

		assertEquals(userService.getById(1).getName(), user.getName());
	}

	@Test
	public void shouldGet1UserByCreate1User() {
		User user = User.builder()
				.login("userLogin")
				.email("userEmail@ya.ru")
				.name("UserName")
				.birthday(LocalDate.of(2000, 1, 1))
				.build();
		userService.createUser(user);

		assertEquals(userService.getUsers().size(), 1);
	}

	@Test
	public void shouldGetUserWithId1ById1() {
		User user = User.builder()
				.login("userLogin")
				.email("userEmail@ya.ru")
				.name("UserName")
				.birthday(LocalDate.of(2000, 1, 1))
				.build();
		userService.createUser(user);

		assertEquals(userService.getById(1), user);
	}

	@Test
	public void shouldSuccessAddFriendship() {
		User user = User.builder()
				.login("userLogin")
				.email("userEmail@ya.ru")
				.name("UserName")
				.birthday(LocalDate.of(2000, 1, 1))
				.build();
		userService.createUser(user);

		User user2 = User.builder()
				.login("user2Login")
				.email("user2Email@ya.ru")
				.name("User2Name")
				.birthday(LocalDate.of(2002, 1, 1))
				.build();
		userService.createUser(user2);

		userService.addFriendship(1,2);
		assertEquals(userService.getById(2).getFriends().size(), 1);
	}

	@Test
	public void shouldFailedAddFriendship() {
		User user = User.builder()
				.login("userLogin")
				.email("userEmail@ya.ru")
				.name("UserName")
				.birthday(LocalDate.of(2000, 1, 1))
				.build();
		userService.createUser(user);

		assertThrows(ValidationException.class, () -> userService.addFriendship(1,1));
	}

	@Test
	public void shouldSuccessDeleteFriendship() {
		User user = User.builder()
				.login("userLogin")
				.email("userEmail@ya.ru")
				.name("UserName")
				.birthday(LocalDate.of(2000, 1, 1))
				.build();
		userService.createUser(user);

		User user2 = User.builder()
				.login("user2Login")
				.email("user2Email@ya.ru")
				.name("User2Name")
				.birthday(LocalDate.of(2002, 1, 1))
				.build();
		userService.createUser(user2);

		userService.addFriendship(1,2);
		userService.deleteFriendship(1, 2);
		assertEquals(userService.getById(2).getFriends().size(), 0);
	}

	@Test
	public void shouldFailedDeleteFriendship() {
		User user = User.builder()
				.login("userLogin")
				.email("userEmail@ya.ru")
				.name("UserName")
				.birthday(LocalDate.of(2000, 1, 1))
				.build();
		userService.createUser(user);

		User user2 = User.builder()
				.login("user2Login")
				.email("user2Email@ya.ru")
				.name("User2Name")
				.birthday(LocalDate.of(2002, 1, 1))
				.build();
		userService.createUser(user2);

		assertThrows(ValidationException.class, () -> userService.deleteFriendship(1, 2));
	}

	@Test
	public void shouldGetFriendshipWithId1ById1() {
		User user = User.builder()
				.login("userLogin")
				.email("userEmail@ya.ru")
				.name("UserName")
				.birthday(LocalDate.of(2000, 1, 1))
				.build();
		userService.createUser(user);

		User user2 = User.builder()
				.login("user2Login")
				.email("user2Email@ya.ru")
				.name("User2Name")
				.birthday(LocalDate.of(2002, 1, 1))
				.build();
		userService.createUser(user2);

		userService.addFriendship(1,2);

		assertEquals(userService.getFriendshipById(2).size(), 1);
	}

	@Test
	public void shouldSuccessGetMutualFriendship() {
		User user = User.builder()
				.login("userLogin")
				.email("userEmail@ya.ru")
				.name("UserName")
				.birthday(LocalDate.of(2000, 1, 1))
				.build();
		userService.createUser(user);

		User user2 = User.builder()
				.login("user2Login")
				.email("user2Email@ya.ru")
				.name("User2Name")
				.birthday(LocalDate.of(2002, 1, 1))
				.build();
		userService.createUser(user2);

		User user3 = User.builder()
				.login("user3Login")
				.email("user3Email@ya.ru")
				.name("User3Name")
				.birthday(LocalDate.of(2003, 1, 1))
				.build();
		userService.createUser(user3);

		userService.addFriendship(1,2);
		userService.addFriendship(3,2);

		assertEquals(userService.getMutualFriendship(1,3).size(), 1);
	}

	// тесты для валидации сущностей Film
    @Test
	public void shouldSuccessValidationByCorrectFilm() {
		Film film = Film.builder()
				.name("filmName")
				.description("filmDescription")
				.releaseDate(LocalDate.of(2000, 1, 1))
				.duration(90)
				.build();

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(0, violations.size());
	}

	@Test
	public void shouldFailedValidationByFilmWithoutName() {
		Film film = Film.builder()
				.name("")
				.description("filmDescription")
				.releaseDate(LocalDate.of(2000, 1, 1))
				.duration(90)
				.build();

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(1, violations.size(), "Film name is empty");
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

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(0, violations.size());
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

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(1, violations.size(), "Wrong size of film description");
	}

	@Test
	public void shouldSuccessValidationByFilmReleaseDate18951228() {
		Film film = Film.builder()
				.name("filmName")
				.description("filmDescription")
				.releaseDate(LocalDate.of(1895, 12, 28))
				.duration(90)
				.build();

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(0, violations.size());
	}

	@Test
	public void shouldFailedValidationByFilmReleaseDate18951227() {
		Film film = Film.builder()
				.name("filmName")
				.description("filmDescription")
				.releaseDate(LocalDate.of(1895, 12, 27))
				.duration(90)
				.build();

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(1, violations.size(), "Wrong film release date");
	}

	@Test
	public void shouldSuccessValidationByFilmDuration1() {
		Film film = Film.builder()
				.name("filmName")
				.description("filmDescription")
				.releaseDate(LocalDate.of(2000, 1, 1))
				.duration(1)
				.build();

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(0, violations.size());
	}

	@Test
	public void shouldFailedValidationByFilmDuration0() {
		Film film = Film.builder()
				.name("filmName")
				.description("filmDescription")
				.releaseDate(LocalDate.of(2000, 1, 1))
				.duration(0)
				.build();

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(1, violations.size(), "Wrong film duration");
	}

	// тесты для валидации сущностей User
	@Test
	public void shouldSuccessValidationByCorrectUser() {
		User user = User.builder()
				.login("userLogin")
				.email("userEmail@ya.ru")
				.name("UserName")
				.birthday(LocalDate.of(2000, 1, 1))
				.build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(0, violations.size());
	}

	@Test
	public void shouldFailedValidationByBlankUserEmail() {
		User user = User.builder()
				.login("userLogin")
				.email("")
				.name("UserName")
				.birthday(LocalDate.of(2000, 1, 1))
				.build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size(), "User email is black");
	}

	@Test
	public void shouldFailedValidationByUserEmailWithoutEmailSymbol() {
		User user = User.builder()
				.login("userLogin")
				.email("userEmail.ya.ru")
				.name("UserName")
				.birthday(LocalDate.of(2000, 1, 1))
				.build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size(), "User email without email symbol");
	}

	@Test
	public void shouldFailedValidationByEmptyUserLogin() {
		User user = User.builder()
				.login("")
				.email("userEmail@ya.ru")
				.name("UserName")
				.birthday(LocalDate.of(2000, 1, 1))
				.build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size(), "User login is empty");
	}

	@Test
	public void shouldFailedValidationByUserLoginWithSpace() {
		User user = User.builder()
				.login("user Login")
				.email("userEmail@ya.ru")
				.name("UserName")
				.birthday(LocalDate.of(2000, 1, 1))
				.build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size(), "User login with space");
	}

	@Test
	public void shouldSuccessValidationByEmptyUserName() {
		User user = User.builder()
				.login("userLogin")
				.email("userEmail@ya.ru")
				.name("")
				.birthday(LocalDate.of(2000, 1, 1))
				.build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(0, violations.size());
	}

	@Test
	public void shouldEqualsLoginAndNameByEmptyUserName() {
		User user = User.builder()
				.login("userLogin")
				.email("userEmail@ya.ru")
				.name("")
				.birthday(LocalDate.of(2000, 1, 1))
				.build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(0, violations.size());
	}

	@Test
	public void shouldSuccessValidationByUserBirthdayNow() {
		User user = User.builder()
				.login("userLogin")
				.email("userEmail@ya.ru")
				.name("UserName")
				.birthday(LocalDate.now())
				.build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(0, violations.size());
	}

	@Test
	public void shouldFailedValidationByUserBirthdayInFuture() {
		User user = User.builder()
				.login("userLogin")
				.email("userEmail@ya.ru")
				.name("UserName")
				.birthday(LocalDate.of(2024, 1, 1))
				.build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size(), "Wrong user birthday");
	}
}