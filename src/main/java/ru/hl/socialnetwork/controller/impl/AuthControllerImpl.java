package ru.hl.socialnetwork.controller.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.hl.socialnetwork.controller.AuthController;
import ru.hl.socialnetwork.model.dto.request.profile.RegisterProfileRequestDto;
import ru.hl.socialnetwork.service.ProfileService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

  private final ProfileService profileService;

  @Override
  public ResponseEntity<Void> register(RegisterProfileRequestDto registerProfileRequestDto) {
    profileService.register(registerProfileRequestDto);
    return ResponseEntity.noContent().build();
  }

}
