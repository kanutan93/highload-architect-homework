package ru.hl.coreservice.service;

import ru.hl.coreservice.model.dto.request.profile.RegisterProfileRequestDto;
import ru.hl.coreservice.model.dto.request.profile.UpdateProfileRequestDto;
import ru.hl.coreservice.model.dto.response.ProfileResponseDto;

import java.util.List;

public interface ProfileService {

  void register(RegisterProfileRequestDto registerProfileRequestDto);

  ProfileResponseDto getCurrentProfile();

  ProfileResponseDto updateCurrentProfile(UpdateProfileRequestDto updateProfileRequestDto);

  List<ProfileResponseDto> getUserProfiles(String search, Integer page, Integer limit);

  List<ProfileResponseDto> getUserProfiles(String firstName, String lastName);

  ProfileResponseDto getUserProfile(Integer userId);


}
