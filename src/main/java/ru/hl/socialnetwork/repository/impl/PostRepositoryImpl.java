package ru.hl.socialnetwork.repository.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.hl.socialnetwork.mapper.post.PostDaoRowMapper;
import ru.hl.socialnetwork.model.dao.PostDao;
import ru.hl.socialnetwork.repository.PostRepository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository {

  private final JdbcTemplate jdbcTemplate;

  @Override
  public List<PostDao> getAllPostsFromFriends(Integer userId) {
    return jdbcTemplate.query("SELECT * FROM posts " +
            "WHERE author_user_id IN (SELECT receiver_id FROM friends WHERE sender_id = ? AND is_approved = true) " +
            "OR author_user_id IN (SELECT sender_id FROM friends WHERE receiver_id = ? AND is_approved = true) " +
            "AND author_user_id != ? " +
            "ORDER BY created_at DESC " +
            "LIMIT 1000",
        new PostDaoRowMapper(),
        userId);
  }

  @Override
  public void createPost(String text, Integer authorUserId) {
    jdbcTemplate.update("INSERT INTO posts (text, author_user_id) " +
            "VALUES (?, ?)",
        text, authorUserId);
  }

  @Override
  public void updatePost(Integer id, String text, Integer authorUserId) {
    jdbcTemplate.update("UPDATE posts " +
            "SET text = ? " +
            "WHERE id = ? AND author_user_id = ?",
        text, id, authorUserId);
  }

  @Override
  public void deletePost(Integer id) {
    jdbcTemplate.update("DELETE FROM posts " +
            "WHERE id = ?",
        id);
  }
}
