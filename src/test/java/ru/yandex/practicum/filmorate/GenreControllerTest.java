package ru.yandex.practicum.filmorate;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;

@JdbcTest
@AutoConfigureTestDatabase
public class GenreControllerTest {
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//    private GenreRepository genreRepository;
//
//
//    @BeforeEach
//    public void setUp() {
//        RowMapper<Genre> genreRowMapper = new BeanPropertyRowMapper<>(Genre.class);
//        genreRepository = new GenreRepository(jdbcTemplate, genreRowMapper);
//
//    }
//
//    @Test
//    void allGenres_shouldReturnAllGenres() {
//        Collection<Genre> genres = genreRepository.allGenres();
//        assertEquals(6, genres.size());
//    }
//
//    @Test
//    void findById_shouldReturnGenre_whenExists() {
//        Genre genre = genreRepository.findById(1);
//        assertNotNull(genre);
//        assertEquals("Комедия", genre.getName());
//    }
//
//    @Test
//    void findById_shouldReturnNull_whenNotExists() {
//
//        assertThrows(NotFoundException.class, () -> {
//            genreRepository.findById(999);
//        });
//    }


}
