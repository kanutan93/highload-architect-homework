package ru.hl.socialnetwork.mapper.user;

import org.mapstruct.Mapper;
import ru.hl.socialnetwork.model.dao.UserDao;
import ru.hl.socialnetwork.model.dto.request.RegisterProfileRequestDto;
import ru.hl.socialnetwork.model.dto.response.ProfileResponseDto;

@Mapper
public interface UserDaoMapper {

  UserDao toUserDao(RegisterProfileRequestDto registerProfileRequestDto);

  ProfileResponseDto toProfileResponseDto(UserDao userDao);
}
