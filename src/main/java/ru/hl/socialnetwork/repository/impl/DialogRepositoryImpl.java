package ru.hl.socialnetwork.repository.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.hl.socialnetwork.mapper.dialog.MessageDaoRowMapper;
import ru.hl.socialnetwork.model.dao.MessageDao;
import ru.hl.socialnetwork.repository.DialogRepository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class DialogRepositoryImpl implements DialogRepository {

  private final JdbcTemplate jdbcTemplate;

  @Override
  public Integer getDialogId(Integer currentUserId, Integer userId) {
    try {
      return jdbcTemplate.queryForObject("SELECT d.id FROM dialog AS d " +
              "WHERE (d.user1_id = ? AND d.user2_id = ?) OR (d.user2_id = ? AND d.user1_id = ?) ",
          Integer.class,
          currentUserId, userId, currentUserId, userId);
    } catch (Exception e) {
      log.info("Dialog not found for user with id: {} and current user with id: {}", userId, currentUserId);
      return null;
    }
  }

  @Override
  public Integer createDialog(Integer currentUserId, Integer userId) {
    return jdbcTemplate.queryForObject("INSERT INTO dialog (user1_id, user2_id) " +
            "VALUES (?, ?) " +
            "RETURNING id",
        Integer.class,
        currentUserId, userId);
  }

  @Override
  public List<MessageDao> getMessages(Integer currentUserId, Integer userId) {
    return jdbcTemplate.query("SELECT m.id, m.from_id, m.to_id, m.text, m.dialog_id FROM dialog AS d " +
            "JOIN message AS m ON d.id = m.dialog_id " +
            "WHERE (d.user1_id = ? AND d.user2_id = ?) OR (d.user2_id = ? AND d.user1_id = ?) " +
            "ORDER BY m.created_at ASC",
        new MessageDaoRowMapper(),
        currentUserId, userId, currentUserId, userId);
  }

  @Override
  public void createMessage(MessageDao messageDao) {
    jdbcTemplate.update("INSERT INTO message (from_id, to_id, text, dialog_id) " +
            "VALUES (?, ?, ?, ?)",
        messageDao.getFrom(), messageDao.getTo(), messageDao.getText(), messageDao.getDialogId());
  }
}
