package ru.hl.socialnetwork.repository.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.hl.socialnetwork.mapper.friend.FriendDaoRowMapper;
import ru.hl.socialnetwork.mapper.user.UserDaoRowMapper;
import ru.hl.socialnetwork.model.dao.FriendDao;
import ru.hl.socialnetwork.model.dao.UserDao;
import ru.hl.socialnetwork.repository.FriendRepository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class FriendRepositoryImpl implements FriendRepository {

  private final JdbcTemplate jdbcTemplate;

  @Override
  public List<FriendDao> getFriendRequests(Integer currentUserId) {
    return jdbcTemplate.query("SELECT * FROM friends " +
            "WHERE receiver_id = ?",
        new FriendDaoRowMapper(),
        currentUserId);
  }

  @Override
  public  FriendDao get(Integer senderId, Integer receiverId) {
    return jdbcTemplate.queryForObject("SELECT * FROM friends " +
            "WHERE (sender_id = ? AND receiver_id = ?) OR (receiver_id = ? AND sender_id = ?)",
        new FriendDaoRowMapper(),
        senderId, receiverId, senderId, receiverId);
  }

  @Override
  public void save(FriendDao friendDao) {
    jdbcTemplate.update("INSERT INTO friends (sender_id, receiver_id, is_approved) " +
            "VALUES (?, ?, ?)",
        friendDao.getSenderId(), friendDao.getReceiverId(), friendDao.isApproved());
  }

  @Override
  public void update(Integer id, FriendDao friendDao) {
    jdbcTemplate.update("UPDATE friends " +
            "SET is_approved = ? " +
            "WHERE id = ?",
        friendDao.isApproved(), id);
  }

  @Override
  public void remove(Integer senderId, Integer receiverId) {
    jdbcTemplate.update("DELETE FROM friends " +
            "WHERE (sender_id = ? AND receiver_id = ?) OR (receiver_id = ? AND sender_id = ?)",
        senderId, receiverId, senderId, receiverId);
  }
}
