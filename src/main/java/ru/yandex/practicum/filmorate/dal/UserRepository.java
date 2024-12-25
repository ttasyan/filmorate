package ru.yandex.practicum.filmorate.dal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;

@Repository
public class UserRepository extends BaseRepository<User> implements UserStorage {

    private static final String FIND_ALL_QUERY = "SELECT * FROM users";
    private static final String INSERT_QUERY = "INSERT INTO users(login, name, email, birthday)" +
            "VALUES (?, ?, ?, ?) ";
    private static final String UPDATE_QUERY = "UPDATE users SET login = ?, name = ?, email = ?, birthday = ? WHERE user_id = ?";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM users WHERE user_id =?";

    public UserRepository(JdbcTemplate jdbc, RowMapper<User> mapper) {
        super(jdbc, mapper);
    }

    @Override
    public User getUserById(Integer id) {
        return findOne(FIND_BY_ID_QUERY, id);
    }

    @Override
    public Collection<User> allUsers() {
        return findMany(FIND_ALL_QUERY);
    }

    @Override
    public User create(User user) {
        Integer id = insert(INSERT_QUERY,
                user.getLogin(),
                user.getName(),
                user.getEmail(),
                user.getBirthday());
        user.setId(id);
        return user;
    }

    @Override
    public User update(User user) {
        update(UPDATE_QUERY, user.getLogin(),
                user.getName(),
                user.getEmail(),
                user.getBirthday(),
                user.getId());
        return user;
    }

}
