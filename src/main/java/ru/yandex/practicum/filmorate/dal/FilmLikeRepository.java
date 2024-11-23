package ru.yandex.practicum.filmorate.dal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;

@Repository
public class FilmLikeRepository extends BaseRepository<Film> {
    public FilmLikeRepository(JdbcTemplate jdbc, RowMapper<Film> mapper) {
        super(jdbc, mapper);
    }

    private static final String ADD_QUERY = "INSERT INTO likes (film_id, user_id) VALUES (?, ?)";
    private static final String DELETE_QUERY = "DELETE FROM likes WHERE film_id = ? AND user_id = ?";

    public void addLike(Integer id, Integer userId) {
        insert(ADD_QUERY, id, userId);
    }

    public void deleteLike(Integer id, Integer userId) {
         delete(DELETE_QUERY, id, userId);
    }

}
