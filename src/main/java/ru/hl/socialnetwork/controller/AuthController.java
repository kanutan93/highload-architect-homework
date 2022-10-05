package ru.hl.socialnetwork.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.hl.socialnetwork.model.dto.request.RegisterProfileRequestDto;

@RequestMapping("/api/auth")
public interface AuthController {

  @PostMapping("/register")
  ResponseEntity<Void> register(@RequestBody RegisterProfileRequestDto registerProfileRequestDto);
}
