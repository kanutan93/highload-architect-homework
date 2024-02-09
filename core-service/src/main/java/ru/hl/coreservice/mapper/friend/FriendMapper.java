package ru.hl.coreservice.mapper.friend;

import org.mapstruct.Mapper;
import ru.hl.coreservice.model.dao.FriendDao;
import ru.hl.coreservice.model.dto.response.FriendRequestsResponseDto;

@Mapper
public interface FriendMapper {


  FriendRequestsResponseDto toFriendRequestResponseDto(FriendDao friendDao);
}
