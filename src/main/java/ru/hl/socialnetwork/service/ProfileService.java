package ru.hl.socialnetwork.service;

import ru.hl.socialnetwork.dto.request.RegisterProfileRequestDto;
import ru.hl.socialnetwork.dto.response.ProfileResponseDto;

public interface ProfileService {

  void register(RegisterProfileRequestDto registerProfileRequestDto);

  ProfileResponseDto getCurrentProfile();

  ProfileResponseDto getUserProfile(Integer id);

  void addToUserProfileToFriends(Integer id);
}
