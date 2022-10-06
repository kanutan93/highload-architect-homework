package ru.hl.socialnetwork.repository;

import ru.hl.socialnetwork.model.dao.UserDao;

import java.util.List;

public interface UserRepository {

  void save(UserDao userDao);

  void update(Integer id, UserDao userDao);

  List<UserDao> search(String currentUserEmail, String search, Integer page, Integer limit);

  UserDao getById(Integer id);

  UserDao getByEmail(String email);
}
