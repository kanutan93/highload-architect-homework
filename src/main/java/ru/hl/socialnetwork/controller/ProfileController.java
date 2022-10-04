package ru.hl.socialnetwork.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.hl.socialnetwork.dto.response.ProfileResponseDto;

@RequestMapping("/api/profile")
public interface ProfileController {

  @GetMapping("/my-profile")
  ResponseEntity<ProfileResponseDto> getCurrentProfile();

  @GetMapping("/user-profile/{id}")
  ResponseEntity<ProfileResponseDto> getUserProfile(@PathVariable("id") Integer id);

  @PostMapping("/user-profile/{id}/add-to-friend")
  ResponseEntity<Void> addToFriend(@PathVariable("id") Integer id);
}
