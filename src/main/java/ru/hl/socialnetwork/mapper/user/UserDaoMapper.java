package ru.hl.socialnetwork.mapper.user;

import org.mapstruct.Mapper;
import ru.hl.socialnetwork.dao.UserDao;
import ru.hl.socialnetwork.dto.request.RegisterProfileRequestDto;
import ru.hl.socialnetwork.dto.response.ProfileResponseDto;

@Mapper
public interface UserDaoMapper {

  UserDao toUserDao(RegisterProfileRequestDto registerProfileRequestDto);

  ProfileResponseDto toProfileResponseDto(UserDao userDao);
}
