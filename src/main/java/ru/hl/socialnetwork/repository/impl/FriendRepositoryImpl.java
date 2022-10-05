package ru.hl.socialnetwork.repository.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.hl.socialnetwork.model.dao.FriendDao;
import ru.hl.socialnetwork.repository.FriendRepository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class FriendRepositoryImpl implements FriendRepository {

  private final JdbcTemplate jdbcTemplate;

  @Override
  public void save(FriendDao friendDao) {
    jdbcTemplate.update("INSERT INTO friends (sender_id, receiver_id, is_approved) VALUES (?, ?, ?)",
        friendDao.getSenderId(), friendDao.getReceiverId(), friendDao.isApproved());
  }

  @Override
  public void removeBySenderIdOrReceiverId(Integer id) {
    jdbcTemplate.update("DELETE FROM friends WHERE sender_id = ? OR receiver_id = ?",
        id, id);
  }
}
