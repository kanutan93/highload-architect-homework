package ru.hl.primaryservice.service;

import ru.hl.primaryservice.model.dto.request.profile.RegisterProfileRequestDto;
import ru.hl.primaryservice.model.dto.request.profile.UpdateProfileRequestDto;
import ru.hl.primaryservice.model.dto.response.ProfileResponseDto;

import java.util.List;

public interface ProfileService {

  void register(RegisterProfileRequestDto registerProfileRequestDto);

  ProfileResponseDto getCurrentProfile();

  ProfileResponseDto updateCurrentProfile(UpdateProfileRequestDto updateProfileRequestDto);

  List<ProfileResponseDto> getUserProfiles(String search, Integer page, Integer limit);

  List<ProfileResponseDto> getUserProfiles(String firstName, String lastName);

  ProfileResponseDto getUserProfile(Integer userId);


}
