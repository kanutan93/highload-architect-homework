package ru.hl.primaryservice.repository.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.hl.primaryservice.mapper.post.PostDaoRowMapper;
import ru.hl.primaryservice.model.dao.PostDao;
import ru.hl.primaryservice.repository.PostRepository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository {

  private final JdbcTemplate jdbcTemplate;

  @Override
  public List<PostDao> getAllPostsFromFriends(Integer userId) {
    return jdbcTemplate.query("SELECT * FROM post " +
            "WHERE author_user_id IN (SELECT receiver_id FROM friends WHERE sender_id = ? AND is_approved = true) " +
            "OR author_user_id IN (SELECT sender_id FROM friends WHERE receiver_id = ? AND is_approved = true) " +
            "AND author_user_id != ? " +
            "LIMIT 1000",
        new PostDaoRowMapper(),
        userId, userId, userId);
  }

  @Override
  public PostDao getPostById(Integer id) {
    return jdbcTemplate.queryForObject("SELECT * FROM post " +
            "WHERE id = ?",
        new PostDaoRowMapper(),
        id);
  }

  @Override
  public int createPost(String text, Integer authorUserId) {
    GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(connection -> {
      PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO post (text, author_user_id) " +
              "VALUES (?, ?)",
          Statement.RETURN_GENERATED_KEYS);
      preparedStatement.setString(1, text);
      preparedStatement.setInt(2, authorUserId);
      return preparedStatement;
    }, generatedKeyHolder);

    return (int) generatedKeyHolder.getKeys().get("id");
  }

  @Override
  public void updatePost(Integer id, String text, Integer authorUserId) {
    jdbcTemplate.update("UPDATE post " +
            "SET text = ? " +
            "WHERE id = ? AND author_user_id = ?",
        text, id, authorUserId);
  }

  @Override
  public void deletePost(Integer id) {
    jdbcTemplate.update("DELETE FROM post " +
            "WHERE id = ?",
        id);
  }
}
