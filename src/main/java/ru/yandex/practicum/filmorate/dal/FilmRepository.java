package ru.yandex.practicum.filmorate.dal;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;


@Repository
public class FilmRepository extends BaseRepository<Film> implements FilmStorage {

    private static final String FIND_ALL_QUERY = "SELECT * FROM films f JOIN mpa m ON f.mpa_id = m.mpa_id";
    private static final String FIND_POPULAR_QUERY = "SELECT f.*, m.mpa_name FROM films f " +
            "LEFT JOIN likes l ON f.film_id = l.film_id JOIN mpa m ON f.mpa_id = m.mpa_id " +
            "GROUP BY f.film_id ORDER BY COUNT(l.user_id) DESC LIMIT ?";
    private static final String INSERT_QUERY = "INSERT INTO films(name, description, release_date, duration, mpa_id) " +
            "VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE films SET name = ?, description = ?, release_date = ?, " +
            "duration = ? WHERE film_id = ?";
    private static final String ALL_GENRES_FILMS_QUERY = "SELECT * FROM film_genre fg, " +
            "genre g WHERE fg.genre_id = g.genre_id";
    private static final String FIND_BY_ID_QUERY = "SELECT *, m.mpa_name FROM films f JOIN mpa m on f.mpa_id=" +
            "m.mpa_id WHERE film_id = ?";

    public FilmRepository(JdbcTemplate jdbc, RowMapper<Film> mapper) {
        super(jdbc, mapper);
    }

    @Override
    public List<Film> allFilms() {
        return findMany(FIND_ALL_QUERY);
    }

    @Override
    public List<Film> mostLiked(Integer count) {
        return findMany(FIND_POPULAR_QUERY, count);
    }

    @Override
    public Film addFilm(Film film) {
        Integer id = insert(INSERT_QUERY,
                film.getName(),
                film.getDescription(),
                Timestamp.valueOf(film.getReleaseDate().atStartOfDay()),
                film.getDuration(),
                film.getMpa().getId());
        film.setId(id);
        return film;
    }

    @Override
    public Film update(Film newFilm) {
        update(UPDATE_QUERY,
                newFilm.getName(),
                newFilm.getDescription(),
                Timestamp.valueOf(newFilm.getReleaseDate().atStartOfDay()),
                newFilm.getDuration(),
                newFilm.getId());

        return newFilm;

    }

    private Map<Integer, List<Genre>> getAllGenres() {
        Map<Integer, List<Genre>> genres = new HashMap<>();
        return jdbc.query(ALL_GENRES_FILMS_QUERY, (ResultSet rs) -> {
            while (rs.next()) {
                Integer filmId = rs.getInt("film_id");
                Integer genreId = rs.getInt("genre_id");
                String genreName = rs.getString("genre_name");
                genres.computeIfAbsent(filmId, k -> List.of(new Genre(genreId, genreName)));
            }
            return genres;
        });
    }

    private void genreIdValidate(List<Genre> genres) {
        Integer genreMaxId = jdbc.queryForObject("SELECT COUNT(*) FROM genre", Integer.class);
        if (genreMaxId == null) {
            return;
        }
        for (Genre genre : genres) {
            if (genre.getId() > genreMaxId) {
                throw new ValidationException("Указан несуществующий Mpa");
            }
        }
    }

    private void saveGenres(Film film) {
        final Integer filmId = film.getId();
        jdbc.update("delete from FILM_GENRES where FILM_ID = ?", filmId);
        final List<Genre> genres = film.getGenres();
        if (genres == null || genres.isEmpty()) {
            return;
        }
        genreIdValidate(genres);
        final ArrayList<Genre> genreList = new ArrayList<>(genres);
        jdbc.batchUpdate(
                "insert into FILM_GENRES (FILM_ID, GENRE_ID) values (?, ?)",
                new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setInt(1, filmId);
                        ps.setInt(2, genreList.get(i).getId());
                    }

                    public int getBatchSize() {
                        return genreList.size();
                    }
                });
    }

    public Film getFilmById(Integer filmId) {
        return findOne(FIND_BY_ID_QUERY, filmId);
    }
}
