package ru.yandex.practicum.filmorate;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;


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
//
//        Film addedFilm = filmRepository.addFilm(film);
//        assertNotNull(addedFilm);
//        assertNotNull(addedFilm.getId());
//        assertEquals("Test Film", addedFilm.getName());
//        SqlRowSet rowSet = jdbcTemplate.queryForRowSet("SELECT * FROM films WHERE film_id = ?", addedFilm.getId());
//        assertTrue(rowSet.next());
//        assertEquals("Test Film", rowSet.getString("name"));
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

