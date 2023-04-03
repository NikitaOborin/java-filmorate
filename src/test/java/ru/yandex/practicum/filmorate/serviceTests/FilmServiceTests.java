package ru.yandex.practicum.filmorate.serviceTests;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import ru.yandex.practicum.filmorate.exeptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Sql({"/schema.sql", "/data.sql"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class FilmServiceTests {
    private final FilmService filmService;
    private final UserService userService;
    private final Film film = new Film(1, "name", "description1",
            LocalDate.of(2023, 1, 19), 100, null, new Mpa(1, "G"));
    private final User user = new User(1, "testEmail", "testLogin", "testName",
            LocalDate.of(1989, 3, 12));

    @Test
    public void addAndGetFilmTest() {
        filmService.addFilm(film);
        assertEquals(film, filmService.getById(film.getId()));
    }

    @Test
    public void findAllFilmsTest() {
        filmService.addFilm(film);
        List <Film> allFilms = filmService.getFilms();
        assertEquals(1, allFilms.size());
    }

    @Test
    public void updateFilmTest() {
        filmService.addFilm(film);
        film.setMpa(new Mpa(3, "PG-13"));
        filmService.updateFilm(film);
        Film f = filmService.getById(film.getId());
        assertEquals("PG-13", f.getMpa().getName());
    }

    @Test
    public void deleteFilmTest() {
        filmService.addFilm(film);
        filmService.deleteById(film.getId());
        Assertions.assertThatThrownBy(() -> filmService.getById(film.getId())).isInstanceOf(NotFoundException.class);
    }

    @Test
    public void testUpdateFilmNotFound() {
        film.setId(999);
        Assertions.assertThatThrownBy(() -> filmService.updateFilm(film)).isInstanceOf(NotFoundException.class);
    }

    @Test
    public void addAndDeleteLikeTest() {
        filmService.addFilm(film);
        Film film2 = new Film(2, "name2", "description1",
                LocalDate.of(2023, 1, 19), 100, null, new Mpa(1, "G"));
        filmService.addFilm(film2);
        userService.createUser(user);
        filmService.addLike(1, 1);
        assertEquals(List.of(film, film2), filmService.getBestFilms(3));
        filmService.deleteLike(1, 1);
        filmService.addLike(2, 1);
        assertEquals(List.of(film2, film), filmService.getBestFilms(3));
    }
}
