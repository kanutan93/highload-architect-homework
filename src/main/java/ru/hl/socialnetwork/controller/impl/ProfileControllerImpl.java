package ru.hl.socialnetwork.controller.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.hl.socialnetwork.controller.ProfileController;
import ru.hl.socialnetwork.dto.response.ProfileResponseDto;
import ru.hl.socialnetwork.service.ProfileService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ProfileControllerImpl implements ProfileController {

  private final ProfileService profileService;

  @Override
  public ResponseEntity<ProfileResponseDto> getCurrentProfile() {
    return ResponseEntity.ok(profileService.getCurrentProfile());
  }

  @Override
  public ResponseEntity<ProfileResponseDto> getUserProfile(Integer id) {
    return ResponseEntity.ok(profileService.getUserProfile(id));
  }

  @Override
  public ResponseEntity<Void> addToFriends(Integer id) {
    profileService.addUserProfileToFriends(id);
    return ResponseEntity.noContent().build();
  }

  @Override
  public ResponseEntity<Void> removeFromFriends(Integer id) {
    profileService.removeUserProfileFromFriends(id);
    return ResponseEntity.noContent().build();
  }
}
