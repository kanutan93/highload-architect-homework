package ru.hl.primaryservice.repository;

import ru.hl.primaryservice.model.dao.UserDao;

import java.util.List;

public interface UserRepository {

  void save(UserDao userDao);

  void update(Integer id, UserDao userDao);

  List<UserDao> search(Integer currentUserId, String search, Integer page, Integer limit);

  List<UserDao> searchUsers(String firstName, String lastName);

  UserDao getById(Integer id);

  UserDao getByEmail(String email);
}
