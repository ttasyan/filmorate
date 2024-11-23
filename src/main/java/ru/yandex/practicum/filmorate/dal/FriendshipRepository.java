package ru.yandex.practicum.filmorate.dal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

@Repository
public class FriendshipRepository extends BaseRepository<User> {
    public FriendshipRepository(JdbcTemplate jdbc, RowMapper<User> mapper) {
        super(jdbc, mapper);
    }

    private static final String ADD_QUERY = "INSERT INTO friends(user_id, friend_id) VALUES (?, ?)";
    private static final String DELETE_QUERY = "DELETE FROM friends WHERE user_id = ? AND friend_id = ?";
    private static final String FIND_ALL_FRIENDS_QUERY = "SELECT u.* FROM users u JOIN friends f ON u.id = f.friend_id " +
            "WHERE f.user_id = ?";
    private static final String FIND_COMMON_FRIENDS_QUERY = "SELECT u.* FROM users u JOIN friends f1 ON u.id = f1.friend_id " +
            "JOIN friends f2 ON u.id = f2.friend_id WHERE f1.user_id = ? AND f2.user_id = ?";

    public void addFriend(Integer id, Integer friendId) {
        String status = "Неподтвержденно";
        insert(ADD_QUERY, id, friendId);
    }

    public void deleteFriend(Integer id, Integer friendId) {
        String status = "Неподтвержденно";
        delete(DELETE_QUERY, id, friendId);

    }

    public List<User> allFriends(Integer id) {
        return findMany(FIND_ALL_FRIENDS_QUERY, id);
    }

    public List<User> commonFriends(Integer id, Integer friendId) {
        return findMany(FIND_COMMON_FRIENDS_QUERY, id, friendId);
    }
}
