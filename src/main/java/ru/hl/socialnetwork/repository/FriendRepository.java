package ru.hl.socialnetwork.repository;

import ru.hl.socialnetwork.model.dao.FriendDao;

public interface FriendRepository {

  void save(FriendDao friendDao);

  void removeBySenderIdOrReceiverId(Integer id);

}
