package ru.yandex.practicum.filmorate;

//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
//import org.springframework.jdbc.core.BeanPropertyRowMapper;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.support.rowset.SqlRowSet;
//import org.springframework.test.context.jdbc.Sql;
//import ru.yandex.practicum.filmorate.dal.FilmRepository;
//import ru.yandex.practicum.filmorate.model.Film;
//import ru.yandex.practicum.filmorate.model.Genre;
//import ru.yandex.practicum.filmorate.model.Mpa;
//
//import java.time.LocalDate;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;


@JdbcTest
@AutoConfigureTestDatabase
class FilmorateApplicationTests {
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//    private FilmRepository filmRepository;
//
//    @BeforeEach
//    @Sql("classpath:schema.sql")
//    public void setUp() {
//        filmRepository = new FilmRepository(jdbcTemplate, new BeanPropertyRowMapper<>(Film.class));
//    }
//
//    @Test
//    public void testAddFilm() {
//        Film film = new Film();
//        film.setName("Test Film");
//        film.setDescription("Test Description");
//        film.setReleaseDate(LocalDate.of(2023, 1, 1));
//        film.setDuration(120);
//        film.setMpa(new Mpa(1, "PG"));
//        film.setGenres(List.of(new Genre(1, "Комедия")));
//
//        Film addedFilm = filmRepository.addFilm(film);
//        assertNotNull(addedFilm);
//        assertNotNull(addedFilm.getId());
//        assertEquals("Test Film", addedFilm.getName());
//        SqlRowSet rowSet = jdbcTemplate.queryForRowSet("SELECT * FROM films WHERE film_id = ?", addedFilm.getId());
//        assertTrue(rowSet.next());
//        assertEquals("Test Film", rowSet.getString("name"));
//        assertEquals("Комедия", rowSet.getString("genres"));
//    }
//
//    @Test
//    public void testUpdateFilm() {
//        Film film = new Film();
//        film.setName("Test Film");
//        film.setDescription("Test Description");
//        film.setReleaseDate(LocalDate.of(2023, 1, 1));
//        film.setDuration(120);
//        film.setMpa(new Mpa(1, "PG"));
//        film.setGenres(List.of(new Genre(1, "Комедия")));
//        Film addedFilm = filmRepository.addFilm(film);
//
//        addedFilm.setName("Updated Film");
//        addedFilm.setDescription(" Film");
//        addedFilm.setReleaseDate(LocalDate.of(2023, 1, 23));
//        addedFilm.setDuration(121);
//
//
//        Film updatedFilm = filmRepository.update(addedFilm);
//
//        assertNotNull(updatedFilm);
//        assertEquals("Updated Film", updatedFilm.getName());
//
//        SqlRowSet rowSet = jdbcTemplate.queryForRowSet("SELECT * FROM films WHERE film_id = ?", updatedFilm.getId());
//        assertTrue(rowSet.next());
//        assertEquals("Updated Film", rowSet.getString("name"));
//    }


}

