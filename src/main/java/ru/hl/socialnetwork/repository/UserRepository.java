package ru.hl.socialnetwork.repository;

import ru.hl.socialnetwork.dao.UserDao;

import java.util.List;

public interface UserRepository {

  void save(UserDao userDao);

  List<UserDao> searchUsers(String search, Integer page, Integer limit);

  UserDao getUserById(Integer id);

  UserDao getUserByEmail(String email);
}
