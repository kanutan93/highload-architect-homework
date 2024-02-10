package ru.hl.primaryservice.controller.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.hl.primaryservice.controller.AuthController;
import ru.hl.primaryservice.model.dto.request.profile.RegisterProfileRequestDto;
import ru.hl.primaryservice.service.ProfileService;

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
