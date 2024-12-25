package ru.yandex.practicum.filmorate.dal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Friendship;

import java.util.List;

@Repository
public class FriendshipRepository extends BaseRepository<Friendship> {

    private static final String ADD_QUERY = "INSERT INTO friends(user_id, friend_id, status) VALUES (?, ?, ?)";
    private static final String DELETE_QUERY = "DELETE FROM friends WHERE user_id = ? AND friend_id = ?";
    private static final String FIND_ALL_FRIENDS_QUERY = "SELECT * FROM  friends WHERE user_id = ?";
    private static final String FIND_COMMON_FRIENDS_QUERY = "SELECT * FROM friends WHERE USER_ID = ? AND FRIEND_ID " +
            "IN (SELECT FRIEND_ID FROM friends WHERE USER_ID = ?)";
    private static final String UPDATE_FRIEND_BY_ID = "UPDATE friends SET status = ? " +
            "WHERE user_id = ? AND friend_id = ?";

    public FriendshipRepository(JdbcTemplate jdbc, RowMapper<Friendship> mapper) {
        super(jdbc, mapper);
    }

    public Friendship addFriend(Integer userId, Integer friendId, boolean status) {
        Friendship friendship = new Friendship();
        friendship.setUserId(userId);
        friendship.setFriendId(friendId);
        friendship.setStatus(status);
        int id = jdbc.update(ADD_QUERY, userId, friendId, status);
        return friendship;
    }

    public boolean deleteFriend(Integer id, Integer friendId) {
        return delete(DELETE_QUERY, id, friendId);

    }

    public List<Friendship> allFriends(Integer id) {
        return findMany(FIND_ALL_FRIENDS_QUERY, id);
    }

    public List<Friendship> commonFriends(Integer id, Integer friendId) {
        return findMany(FIND_COMMON_FRIENDS_QUERY, id, friendId);
    }

    public Friendship updateStatus(Integer userId, Integer friendId, boolean status) {
        Friendship friendship = new Friendship();
        friendship.setUserId(userId);
        friendship.setFriendId(friendId);
        friendship.setStatus(status);
        update(UPDATE_FRIEND_BY_ID,
                status,
                userId,
                friendId
        );
        return friendship;
    }
}
