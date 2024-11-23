package ru.yandex.practicum.filmorate.dal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;

@Repository
public class MpaRepository extends BaseRepository<Mpa> {
    public MpaRepository(JdbcTemplate jdbc, RowMapper<Mpa> mapper) {
        super(jdbc, mapper);
    }

    private static final String FIND_ALL_QUERY = "SELECT * FROM mpa";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM mpa WHERE mpa_id = ?";

    public List<Mpa> allRatings() {
        return findMany(FIND_ALL_QUERY);
    }

    public Mpa findById(int id) {
        return findOne(FIND_BY_ID_QUERY, id);
    }
}
