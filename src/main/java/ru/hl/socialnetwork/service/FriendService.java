package ru.hl.socialnetwork.service;

import ru.hl.socialnetwork.model.dto.response.FriendRequestsResponseDto;

import java.util.List;

public interface FriendService {

  List<FriendRequestsResponseDto> getFriendsRequest();

  List<FriendRequestsResponseDto> getAllFriends();

  void addUserProfileToFriends(Integer userId);

  void removeUserProfileFromFriends(Integer userId);
}
