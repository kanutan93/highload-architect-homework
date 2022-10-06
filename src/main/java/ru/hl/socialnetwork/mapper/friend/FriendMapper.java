package ru.hl.socialnetwork.mapper.friend;

import org.mapstruct.Mapper;
import ru.hl.socialnetwork.model.dao.FriendDao;
import ru.hl.socialnetwork.model.dto.response.FriendRequestsResponseDto;

@Mapper
public interface FriendMapper {


  FriendRequestsResponseDto toFriendRequestResponseDto(FriendDao friendDao);
}
