package ru.hl.primaryservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.hl.primaryservice.model.dto.response.FriendRequestsResponseDto;

import java.util.List;

@RequestMapping("/api/friend")
public interface FriendController {


  @GetMapping
  ResponseEntity<List<FriendRequestsResponseDto>> getFriendRequests();

  @PostMapping("/{userId}/add-to-friends")
  ResponseEntity<Void> addToFriends(@PathVariable("userId") Integer userId);

  @DeleteMapping("/{userId}/remove-from-friends")
  ResponseEntity<Void> removeFromFriends(@PathVariable("userId") Integer userId);
}
