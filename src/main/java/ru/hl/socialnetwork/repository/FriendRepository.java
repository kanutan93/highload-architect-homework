package ru.hl.socialnetwork.repository;

import ru.hl.socialnetwork.model.dao.FriendDao;

import java.util.List;

public interface FriendRepository {

  List<FriendDao> getFriendRequests(Integer currentUserId);

  FriendDao get(Integer senderId, Integer receiverId);

  void save(FriendDao friendDao);

  void update(Integer id, FriendDao friendDao);

  void remove(Integer senderId, Integer receiverId) ;

}
