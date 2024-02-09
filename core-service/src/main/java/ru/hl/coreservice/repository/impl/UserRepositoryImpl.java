package ru.hl.coreservice.repository.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.hl.coreservice.mapper.user.UserDaoRowMapper;
import ru.hl.coreservice.model.dao.UserDao;
import ru.hl.coreservice.mapper.user.UserDaoRowMapperWithApproved;
import ru.hl.coreservice.repository.UserRepository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

  private final JdbcTemplate jdbcTemplate;

  @Override
  public void save(UserDao userDao) {
    jdbcTemplate.update("INSERT INTO users (email, password, first_name, last_name, age, sex, about_info, city) " +
            "VALUES (?, ?, ?, ?, ?, ?::SexType, ?, ?)",
        userDao.getEmail(), userDao.getPassword(), userDao.getFirstName(),
        userDao.getLastName(), userDao.getAge(), userDao.getSex().getValue(),
        userDao.getAboutInfo(), userDao.getCity());
  }

  @Override
  public void update(Integer id, UserDao userDao) {
    jdbcTemplate.update("UPDATE users " +
            "SET first_name = ?,last_name = ?,age = ?,sex = ?::SexType,about_info = ?,city = ? " +
            "WHERE id = ? ",
        userDao.getFirstName(),
        userDao.getLastName(), userDao.getAge(), userDao.getSex().getValue(),
        userDao.getAboutInfo(), userDao.getCity(), id);
  }

  @Override
  public List<UserDao> search(Integer currentUserId, String search, Integer page, Integer limit) {
    search = "%" + search + "%";

    return jdbcTemplate.query("SELECT * FROM users as u " +
            "LEFT JOIN (SELECT * FROM friends WHERE friends.sender_id = ? OR friends.receiver_id = ?) as f " +
            "ON (u.id = f.sender_id OR u.id = f.receiver_id) " +
            "WHERE u.id <> ? AND (u.first_name LIKE ? OR u.last_name LIKE ? OR u.email LIKE ?) " +
            "LIMIT ? OFFSET ? ",
        new UserDaoRowMapperWithApproved(),
        currentUserId, currentUserId, currentUserId, search, search, search, limit, page * limit);
  }

  @Override
  public List<UserDao> searchUsers(String firstName, String lastName) {
    firstName = firstName + "%";
    lastName = lastName + "%";
    return jdbcTemplate.query("SELECT * FROM users " +
            "WHERE first_name LIKE ? AND last_name LIKE ? " +
            "ORDER BY id",
        new UserDaoRowMapper(), firstName, lastName);
  }

  @Override
  public UserDao getById(Integer id) {
    return jdbcTemplate.queryForObject(
        "SELECT * FROM users " +
            "LEFT JOIN friends ON (users.id = friends.sender_id OR users.id = friends.receiver_id) " +
            "WHERE users.id = ?", new UserDaoRowMapperWithApproved(), id);
  }

  @Override
  public UserDao getByEmail(String email) {
    return jdbcTemplate.queryForObject(
        "SELECT * FROM users " +
            "WHERE email = ?", new UserDaoRowMapper(), email);
  }
}
