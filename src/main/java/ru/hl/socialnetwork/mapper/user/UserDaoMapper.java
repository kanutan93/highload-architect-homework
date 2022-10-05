package ru.hl.socialnetwork.mapper.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.hl.socialnetwork.model.dao.UserDao;
import ru.hl.socialnetwork.model.dto.request.RegisterProfileRequestDto;
import ru.hl.socialnetwork.model.dto.response.ProfileResponseDto;

@Mapper
public interface UserDaoMapper {

  UserDao toUserDao(RegisterProfileRequestDto registerProfileRequestDto);

  @Mapping(target = "isFriend", source = "isApproved")
  ProfileResponseDto toProfileResponseDto(UserDao userDao);
}
