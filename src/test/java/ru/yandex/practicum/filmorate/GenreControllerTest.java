package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.dal.GenreRepository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@AutoConfigureTestDatabase
public class GenreControllerTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private GenreRepository genreRepository;


    @BeforeEach
    public void setUp() {
        RowMapper<Genre> genreRowMapper = new BeanPropertyRowMapper<>(Genre.class);
        genreRepository = new GenreRepository(jdbcTemplate, genreRowMapper);

    }

    @Test
    void allGenres_shouldReturnAllGenres() {
        Collection<Genre> genres = genreRepository.allGenres();
        assertEquals(6, genres.size());
    }

    @Test
    void findById_shouldReturnGenre_whenExists() {
        Genre genre = genreRepository.findById(1);
        assertNotNull(genre);
        assertEquals("Комедия", genre.getName());
    }

    @Test
    void findById_shouldReturnNull_whenNotExists() {

        assertThrows(NotFoundException.class, () -> {
            genreRepository.findById(999);
        });
    }


}
