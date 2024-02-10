package ru.hl.primaryservice.mapper.friend;

import org.mapstruct.Mapper;
import ru.hl.primaryservice.model.dao.FriendDao;
import ru.hl.primaryservice.model.dto.response.FriendRequestsResponseDto;

@Mapper
public interface FriendMapper {


  FriendRequestsResponseDto toFriendRequestResponseDto(FriendDao friendDao);
}
