package ru.hl.socialnetwork.mapper.post;

import org.mapstruct.Mapper;
import ru.hl.socialnetwork.kafka.payload.PostPayload;
import ru.hl.socialnetwork.model.dao.PostDao;
import ru.hl.socialnetwork.model.dto.response.PostResponseDto;

@Mapper
public interface PostMapper {

  PostResponseDto toPostResponseDto(PostDao postDao);

  PostPayload toPostPayload(Integer receiverUserId, PostPayload.Action action, PostResponseDto postResponseDto);
}
