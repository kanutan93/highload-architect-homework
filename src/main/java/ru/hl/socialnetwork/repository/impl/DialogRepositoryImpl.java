package ru.hl.socialnetwork.repository.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.hl.socialnetwork.mapper.dialog.DialogDaoRowMapper;
import ru.hl.socialnetwork.model.dao.DialogDao;
import ru.hl.socialnetwork.repository.DialogRepository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class DialogRepositoryImpl implements DialogRepository {

  private final JdbcTemplate jdbcTemplate;

  @Override
  public List<DialogDao> getDialogMessages(Integer currentUserId, Integer userId) {
    return jdbcTemplate.query("SELECT * FROM dialog " +
            "WHERE (from_id = ? AND to_id = ?) OR (to_id = ? AND from_id = ?)",
        new DialogDaoRowMapper(),
        currentUserId, userId, currentUserId, userId);
  }

  @Override
  public void createDialogMessage(DialogDao dialogDao) {
    jdbcTemplate.update("INSERT INTO dialog (from_id, to_id, text) " +
            "VALUES (?, ?, ?)",
        dialogDao.getFrom(), dialogDao.getTo(), dialogDao.getText());
  }
}
