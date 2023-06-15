package ru.hl.socialnetwork.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.hl.socialnetwork.model.dto.request.profile.UpdateProfileRequestDto;
import ru.hl.socialnetwork.model.dto.response.ProfileResponseDto;

import java.util.List;

@RequestMapping("/api/profile")
public interface ProfileController {

  @GetMapping("/current-profile")
  ResponseEntity<ProfileResponseDto> getCurrentProfile();

  @PutMapping("/current-profile")
  ResponseEntity<ProfileResponseDto> updateCurrentProfile(@RequestBody UpdateProfileRequestDto updateProfileRequestDto);

  @GetMapping("/user-profiles/search")
  ResponseEntity<List<ProfileResponseDto>> getUserProfiles(@RequestParam("search") String search, @RequestParam("page") Integer page, @RequestParam("limit") Integer limit);

  @GetMapping("/user-profiles/search-by-firstname-and-lastname")
  ResponseEntity<List<ProfileResponseDto>> getUserProfilesByFirstNameAndLastName(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName);

  @GetMapping("/user-profile/{userId}")
  ResponseEntity<ProfileResponseDto> getUserProfile(@PathVariable("userId") Integer userId);
}
