package ru.hl.socialnetwork.mapper.user;

import org.springframework.jdbc.core.RowMapper;
import ru.hl.socialnetwork.model.dao.UserDao;
import ru.hl.socialnetwork.model.enums.SexEnum;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoRowMapperWithApproved implements RowMapper<UserDao> {

  @Override
  public UserDao mapRow(ResultSet rs, int rowNum) throws SQLException {
    UserDao userDao = new UserDao();

    userDao.setId(rs.getInt("id"));
    userDao.setEmail(rs.getString("email"));
    userDao.setFirstName(rs.getString("first_name"));
    userDao.setLastName(rs.getString("last_name"));
    userDao.setAge(rs.getShort("age"));
    userDao.setSex(SexEnum.fromValue(rs.getString("sex")));
    userDao.setAboutInfo(rs.getString("about_info"));
    userDao.setCity(rs.getString("city"));
    userDao.setIsApproved(rs.getBoolean("is_approved"));

    return userDao;
  }
}
