package ru.hl.primaryservice.controller.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.hl.primaryservice.controller.ProfileController;
import ru.hl.primaryservice.model.dto.request.profile.UpdateProfileRequestDto;
import ru.hl.primaryservice.model.dto.response.ProfileResponseDto;
import ru.hl.primaryservice.service.ProfileService;

import java.util.List;

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
  public ResponseEntity<ProfileResponseDto> updateCurrentProfile(UpdateProfileRequestDto updateProfileRequestDto) {
    return ResponseEntity.ok(profileService.updateCurrentProfile(updateProfileRequestDto));
  }

  @Override
  public ResponseEntity<List<ProfileResponseDto>> getUserProfiles(String search, Integer page, Integer limit) {
    return ResponseEntity.ok(profileService.getUserProfiles(search, page, limit));
  }

  @Override
  public ResponseEntity<List<ProfileResponseDto>> getUserProfilesByFirstNameAndLastName(String firstName, String lastName) {
    return ResponseEntity.ok(profileService.getUserProfiles(firstName, lastName));
  }

  @Override
  public ResponseEntity<ProfileResponseDto> getUserProfile(Integer userId) {
    return ResponseEntity.ok(profileService.getUserProfile(userId));
  }
}
