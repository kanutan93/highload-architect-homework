package ru.hl.socialnetwork.mapper.post;

import org.springframework.jdbc.core.RowMapper;
import ru.hl.socialnetwork.model.dao.PostDao;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PostDaoRowMapper implements RowMapper<PostDao> {

    @Override
    public PostDao mapRow(ResultSet rs, int rowNum) throws SQLException {
        PostDao postDao = new PostDao();

        postDao.setId(rs.getInt("id"));
        postDao.setText(rs.getString("text"));
        postDao.setAuthorUserId(rs.getInt("author_user_id"));

        return postDao;
    }
}
