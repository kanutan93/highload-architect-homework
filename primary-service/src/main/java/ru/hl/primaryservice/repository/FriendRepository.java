package ru.hl.primaryservice.repository;

import ru.hl.primaryservice.model.dao.FriendDao;

import java.util.List;

public interface FriendRepository {

  List<FriendDao> getFriendRequests(Integer currentUserId);

  List<FriendDao> getAllFriends(Integer currentUserId);

  FriendDao get(Integer senderId, Integer receiverId);

  void save(FriendDao friendDao);

  void update(Integer id, FriendDao friendDao);

  void remove(Integer senderId, Integer receiverId) ;

}
