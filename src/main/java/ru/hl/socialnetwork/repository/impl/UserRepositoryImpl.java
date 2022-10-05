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
  public List<UserDao> searchUsers(String search, Integer page, Integer limit) {
    search = "%" + search + "%";
    return jdbcTemplate.query("SELECT * FROM users " +
            "WHERE first_name LIKE ? OR last_name LIKE ? OR email LIKE ? " +
            "LIMIT ? OFFSET ? ",
        new UserDaoRowMapper(),
        search, search, search, limit, page * limit);
  }

  @Override
  public UserDao getUserById(Integer id) {
    return jdbcTemplate.queryForObject(
        "SELECT * FROM users " +
            "WHERE id = ?", new UserDaoRowMapper(), id);
  }

  @Override
  public UserDao getUserByEmail(String email) {
    return jdbcTemplate.queryForObject(
        "SELECT * FROM users " +
            "WHERE email = ?", new UserDaoRowMapper(), email);
  }
}
