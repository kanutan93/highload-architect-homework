package ru.hl.primaryservice.controller.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.hl.primaryservice.controller.FriendController;
import ru.hl.primaryservice.model.dto.response.FriendRequestsResponseDto;
import ru.hl.primaryservice.service.FriendService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FriendControllerImpl implements FriendController {

  private final FriendService friendService;

  @Override
  public ResponseEntity<List<FriendRequestsResponseDto>> getFriendRequests() {
    List<FriendRequestsResponseDto> result = friendService.getFriendsRequest();
    return ResponseEntity.ok(result);
  }

  @Override
  public ResponseEntity<Void> addToFriends(Integer userId) {
    friendService.addUserProfileToFriends(userId);
    return ResponseEntity.noContent().build();
  }

  @Override
  public ResponseEntity<Void> removeFromFriends(Integer userId) {
    friendService.removeUserProfileFromFriends(userId);
    return ResponseEntity.noContent().build();
  }
}
