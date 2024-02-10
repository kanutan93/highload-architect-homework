package ru.hl.dialogservice.mapper.dialog;

import org.springframework.jdbc.core.RowMapper;
import ru.hl.dialogservice.model.dao.MessageDao;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MessageDaoRowMapper implements RowMapper<MessageDao> {

    @Override
    public MessageDao mapRow(ResultSet rs, int rowNum) throws SQLException {
        MessageDao messageDao = new MessageDao();

        messageDao.setId(rs.getInt("id"));
        messageDao.setText(rs.getString("text"));
        messageDao.setFrom(rs.getInt("from_id"));
        messageDao.setTo(rs.getInt("to_id"));
        messageDao.setDialogId(rs.getInt("dialog_id"));

        return messageDao;
    }
}
