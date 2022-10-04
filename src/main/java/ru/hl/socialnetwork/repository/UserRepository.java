package ru.hl.socialnetwork.repository;

import ru.hl.socialnetwork.dao.UserDao;

public interface UserRepository {

  void save(UserDao userDao);

  UserDao getUserById(Integer id);

  UserDao getUserByEmail(String email);
}
