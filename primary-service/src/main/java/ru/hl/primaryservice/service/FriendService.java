package ru.hl.primaryservice.service;

import ru.hl.primaryservice.model.dto.response.FriendRequestsResponseDto;

import java.util.List;

public interface FriendService {

  List<FriendRequestsResponseDto> getFriendsRequest();

  List<Integer> getAllFriendsIds();

  void addUserProfileToFriends(Integer userId);

  void removeUserProfileFromFriends(Integer userId);
}
