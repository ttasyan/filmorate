package ru.yandex.practicum.filmorate.dal.mappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Friendship;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class FriendshipRowMapper implements RowMapper<Friendship> {
    @Override
    public Friendship mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Friendship friendship = Friendship.builder().build();
        friendship.setUserId(resultSet.getInt("user_id"));
        friendship.setFriendId(resultSet.getInt("friend_id"));
        friendship.setStatus(resultSet.getBoolean("status"));
        return friendship;
    }
}
