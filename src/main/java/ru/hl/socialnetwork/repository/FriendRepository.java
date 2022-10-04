package ru.hl.socialnetwork.repository;

import ru.hl.socialnetwork.dao.FriendDao;

public interface FriendRepository {

  void save(FriendDao friendDao);

  FriendDao getFriendsById(Integer id);

}
