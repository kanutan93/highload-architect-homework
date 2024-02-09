package ru.hl.coreservice.mapper.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.hl.coreservice.model.dao.UserDao;
import ru.hl.coreservice.model.dto.request.profile.RegisterProfileRequestDto;
import ru.hl.coreservice.model.dto.request.profile.UpdateProfileRequestDto;
import ru.hl.coreservice.model.dto.response.ProfileResponseDto;

@Mapper
public interface UserDaoMapper {

  UserDao toUserDao(RegisterProfileRequestDto registerProfileRequestDto);

  UserDao toUserDao(UpdateProfileRequestDto updateProfileRequestDto);

  @Mapping(target = "isFriend", source = "isApproved")
  ProfileResponseDto toProfileResponseDto(UserDao userDao);
}
