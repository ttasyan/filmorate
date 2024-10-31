package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class FilmControllerTest {
    @Autowired
    FilmController filmController;

    @AfterEach
    public void cleanUp() {
        filmController.allFilms().clear();
    }

    @Test
    void testGetAll() {
        Film film = Film.builder()
                .name("bdb")
                .description("dbb")
                .duration(100)
                .releaseDate(LocalDate.of(1990, 10, 10))
                .build();
        filmController.addFilm(film);
        Film film1 = Film.builder()
                .name("saq")
                .description("kuyll")
                .duration(105)
                .releaseDate(LocalDate.of(1991, 12, 10))
                .build();
        filmController.addFilm(film1);
        filmController.allFilms();
        assertEquals(2, filmController.allFilms().size());
    }

    @Test
    void testPostFilm() {
        Film film = Film.builder()
                .name("film1")
                .description("smth1")
                .duration(100)
                .releaseDate(LocalDate.of(1990, 10, 10))
                .build();
        filmController.addFilm(film);
        assertEquals("film1", film.getName(), "Wrong name");
        assertEquals(filmController.allFilms().size(), 1);


    }

    @Test
    void testPostFilmFail() {
        Film film = Film.builder()
                .name("")
                .description("smth2")
                .duration(100)
                .releaseDate(LocalDate.of(1990, 10, 10))
                .build();
        assertThrows(ValidationException.class, () -> filmController.addFilm(film));

    }

    @Test
    void testPutFilm() {
        Film oldFilm = Film.builder()
                .name("film3")
                .description("smth3")
                .duration(100)
                .releaseDate(LocalDate.of(1990, 10, 10))
                .build();
        filmController.addFilm(oldFilm);
        Film film = Film.builder()
                .id(oldFilm.getId())
                .name("film3")
                .description("smth3")
                .duration(100)
                .releaseDate(LocalDate.of(1990, 12, 21))
                .build();
        filmController.update(film);
        assertEquals(oldFilm.getReleaseDate(), film.getReleaseDate(), "film was not updated");
    }

    @Test
    void testPutFilmFail() {
        Film oldFilm = Film.builder()
                .name("film4")
                .description("smth4")
                .duration(100)
                .releaseDate(LocalDate.of(1990, 10, 10))
                .build();
        filmController.addFilm(oldFilm);
        Film film = Film.builder()
                .name("new film")
                .description("new smth")
                .duration(1)
                .releaseDate(LocalDate.of(1990, 10, 11))
                .build();
        Exception exception = assertThrows(ValidationException.class, () -> filmController.update(film));
        assertTrue(exception.getMessage().contains("должен быть указан"));
    }

}
