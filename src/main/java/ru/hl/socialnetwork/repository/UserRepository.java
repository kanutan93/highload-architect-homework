package ru.hl.socialnetwork.repository;

import ru.hl.socialnetwork.model.dao.UserDao;

import java.util.List;

public interface UserRepository {

  void save(UserDao userDao);

  void update(Integer id, UserDao userDao);

  List<UserDao> search(String currentUserEmail, String search, Integer page, Integer limit);

  List<UserDao> searchUsers(String firstName, String lastName);

  UserDao getById(Integer id);

  UserDao getByEmail(String email);
}
