package ru.hl.socialnetwork.service;

import ru.hl.socialnetwork.model.dto.request.RegisterProfileRequestDto;
import ru.hl.socialnetwork.model.dto.request.UpdateProfileRequestDto;
import ru.hl.socialnetwork.model.dto.response.ProfileResponseDto;

import java.util.List;

public interface ProfileService {

  void register(RegisterProfileRequestDto registerProfileRequestDto);

  ProfileResponseDto getCurrentProfile();

  ProfileResponseDto updateCurrentProfile(UpdateProfileRequestDto updateProfileRequestDto);

  List<ProfileResponseDto> getUserProfiles(String search, Integer page, Integer limit);

  List<ProfileResponseDto> getUserProfiles(String firstName, String lastName);

  ProfileResponseDto getUserProfile(Integer userId);


}
