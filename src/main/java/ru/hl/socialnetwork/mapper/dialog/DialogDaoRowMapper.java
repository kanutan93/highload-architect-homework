package ru.hl.socialnetwork.mapper.dialog;

import org.springframework.jdbc.core.RowMapper;
import ru.hl.socialnetwork.model.dao.DialogDao;
import ru.hl.socialnetwork.model.dao.PostDao;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DialogDaoRowMapper implements RowMapper<DialogDao> {

    @Override
    public DialogDao mapRow(ResultSet rs, int rowNum) throws SQLException {
        DialogDao dialogDao = new DialogDao();

        dialogDao.setId(rs.getInt("id"));
        dialogDao.setText(rs.getString("text"));
        dialogDao.setFrom(rs.getInt("from_id"));
        dialogDao.setTo(rs.getInt("to_id"));

        return dialogDao;
    }
}
