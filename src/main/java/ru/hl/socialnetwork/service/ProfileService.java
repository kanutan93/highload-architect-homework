package ru.hl.socialnetwork.service;

import ru.hl.socialnetwork.model.dto.request.RegisterProfileRequestDto;
import ru.hl.socialnetwork.model.dto.response.ProfileResponseDto;

import java.util.List;

public interface ProfileService {

  void register(RegisterProfileRequestDto registerProfileRequestDto);

  ProfileResponseDto getCurrentProfile();

  List<ProfileResponseDto> getUserProfiles(String search, Integer page, Integer limit);

  ProfileResponseDto getUserProfile(Integer id);

  void addUserProfileToFriends(Integer id);

  void removeUserProfileFromFriends(Integer id);
}
