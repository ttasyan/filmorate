package ru.yandex.practicum.filmorate.dal;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


@Repository
public class FilmRepository extends BaseRepository<Film> {
    public FilmRepository(JdbcTemplate jdbc, RowMapper<Film> mapper) {
        super(jdbc, mapper);
    }

    private static final String FIND_ALL_QUERY = "SELECT * FROM films";
    private static final String FIND_POPULAR_QUERY = "SELECT f.*, COUNT(l.id) AS likes_count FROM films f " +
            "LEFT JOIN likes l ON f.id = l.film_id" +
            "GROUP BY f.id ORDER BY likes_count DESC LIMIT ?";
    private static final String INSERT_QUERY = "INSERT INTO films(name, description, release_date, duration, genres, mpa)" +
            "VALUES (?, ?, ?, ?, ?, ?) returning id";
    private static final String UPDATE_QUERY = "UPDATE films SET name = ?, description = ?, release_date = ?, " +
            "duration = ?, genres = ?, mpa = ? WHERE id = ?";
    private static final String ALL_GENRES_FILMS_QUERY = "SELECT * FROM film_genre fg, " +
            "genre g WHERE fg.genre_id = g.genre_id";

    public Collection<Film> allFilms() {
        Collection<Film> films = findMany(FIND_ALL_QUERY);
        Map<Integer, List<Genre>> genres = getAllGenres();
        for (Film film : films) {
            if (genres.containsKey(film.getId())) {
                film.setGenres(genres.get(film.getId()));
            }
        }
        return films;
    }

    public Collection<Film> popularFilms(Integer count) {
        Collection<Film> films = findMany(FIND_POPULAR_QUERY, count);
        Map<Integer, List<Genre>> genres = getAllGenres();
        for (Film film : films) {
            if (genres.containsKey(film.getId())) {
                film.setGenres(genres.get(film.getId()));
            }
        }
        return films;
    }

    public Film addFilm(Film film) {
        Integer id = insert(INSERT_QUERY,
                film.getDuration(),
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getMpa().getId());
        film.setId(id);
        if (!film.getGenres().isEmpty()) {
            saveGenres(film);
        }
        return film;
    }

    public Film update(Film newFilm) {
        update(UPDATE_QUERY, newFilm.getDuration(),
                newFilm.getName(),
                newFilm.getDescription(),
                newFilm.getReleaseDate(),
                newFilm.getMpa().getId(),
                newFilm.getId());
        if (!newFilm.getGenres().isEmpty()) {
            saveGenres(newFilm);
        }
        return newFilm;

    }

    private Map<Integer, List<Genre>> getAllGenres() {
        Map<Integer, List<Genre>> genres = new HashMap<>();
        return jdbc.query(ALL_GENRES_FILMS_QUERY, (ResultSet rs) -> {
            while (rs.next()) {
                Integer filmId = rs.getInt("film_id");
                Integer genreId = rs.getInt("genre_id");
                String genreName = rs.getString("name");
                genres.computeIfAbsent(filmId, k -> List.of(new Genre(genreId, genreName)));
            }
            return genres;
        });
    }

    private void genreIdValidate(List<Genre> genres) {
        Integer genreMaxId = jdbc.queryForObject("SELECT COUNT(*) FROM mpa", Integer.class);
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


}