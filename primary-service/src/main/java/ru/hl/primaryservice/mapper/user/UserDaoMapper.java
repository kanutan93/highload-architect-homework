package ru.hl.primaryservice.mapper.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.hl.primaryservice.model.dao.UserDao;
import ru.hl.primaryservice.model.dto.request.profile.RegisterProfileRequestDto;
import ru.hl.primaryservice.model.dto.request.profile.UpdateProfileRequestDto;
import ru.hl.primaryservice.model.dto.response.ProfileResponseDto;

@Mapper
public interface UserDaoMapper {

  UserDao toUserDao(RegisterProfileRequestDto registerProfileRequestDto);

  UserDao toUserDao(UpdateProfileRequestDto updateProfileRequestDto);

  @Mapping(target = "isFriend", source = "isApproved")
  ProfileResponseDto toProfileResponseDto(UserDao userDao);
}
