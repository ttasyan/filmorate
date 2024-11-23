package ru.yandex.practicum.filmorate.dal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

@Repository
public class FilmGenreRepository extends BaseRepository<Genre>{
        private static final String INSERT_QUERY = "INSERT INTO FILM_GENRE (film_id, genre_id) VALUES (?, ?)";
        private static final String FIND_BY_FILM_ID_QUERY = "SELECT * FROM FILM_GENRE gf JOIN GENRE g " +
                "ON gf.GENRE_ID = g.GENRE_ID WHERE film_id = ?";

        public FilmGenreRepository(JdbcTemplate jdbc, RowMapper<Genre> mapper) {
            super(jdbc, mapper);
        }

        public List<Genre> getGenresByFilmId(Integer id) {
            return findMany(FIND_BY_FILM_ID_QUERY, id);
        }

        public void addGenreToFilm(Integer filmId, Integer genreId) {
            insert(INSERT_QUERY, filmId, genreId);

        }
    }