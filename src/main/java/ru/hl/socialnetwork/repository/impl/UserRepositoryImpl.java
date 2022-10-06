package ru.hl.socialnetwork.repository.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.hl.socialnetwork.model.dao.UserDao;
import ru.hl.socialnetwork.mapper.user.UserDaoRowMapper;
import ru.hl.socialnetwork.repository.UserRepository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

  private final JdbcTemplate jdbcTemplate;

  @Override
  public void save(UserDao userDao) {
    jdbcTemplate.update("INSERT INTO users (email, password, first_name, last_name, age, sex, about_info, city) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
        userDao.getEmail(), userDao.getPassword(), userDao.getFirstName(),
        userDao.getLastName(), userDao.getAge(), userDao.getSex().getValue(),
        userDao.getAboutInfo(), userDao.getCity());
  }

  @Override
  public void update(Integer id, UserDao userDao) {
    jdbcTemplate.update("UPDATE users " +
            "SET first_name = ?,last_name = ?,age = ?,sex = ?,about_info = ?,city = ? " +
            "WHERE id = ? ",
        id, userDao.getFirstName(),
        userDao.getLastName(), userDao.getAge(), userDao.getSex().getValue(),
        userDao.getAboutInfo(), userDao.getCity());
  }

  @Override
  public List<UserDao> search(String currentUserEmail, String search, Integer page, Integer limit) {
    search = "%" + search + "%";
    return jdbcTemplate.query("SELECT * FROM users " +
            "LEFT JOIN friends ON (users.id = friends.sender_id OR users.id = friends.receiver_id) " +
            "WHERE email <> ? AND (first_name LIKE ? OR last_name LIKE ? OR email LIKE ?) " +
            "LIMIT ? OFFSET ? ",
        new UserDaoRowMapper(),
        currentUserEmail, search, search, search, limit, page * limit);
  }

  @Override
  public UserDao getById(Integer id) {
    return jdbcTemplate.queryForObject(
        "SELECT * FROM users " +
            "LEFT JOIN friends ON (users.id = friends.sender_id OR users.id = friends.receiver_id) " +
            "WHERE users.id = ?", new UserDaoRowMapper(), id);
  }

  @Override
  public UserDao getByEmail(String email) {
    return jdbcTemplate.queryForObject(
        "SELECT * FROM users " +
            "LEFT JOIN friends ON (users.id = friends.sender_id OR users.id = friends.receiver_id) " +
            "WHERE email = ?", new UserDaoRowMapper(), email);
  }
}
