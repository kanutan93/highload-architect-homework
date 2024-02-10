package ru.hl.primaryservice.mapper.post;

import org.mapstruct.Mapper;
import ru.hl.primaryservice.kafka.payload.PostPayload;
import ru.hl.primaryservice.model.dao.PostDao;
import ru.hl.primaryservice.model.dto.response.PostResponseDto;

@Mapper
public interface PostMapper {

  PostResponseDto toPostResponseDto(PostDao postDao);

  PostPayload toPostPayload(Integer receiverUserId, PostPayload.Action action, PostResponseDto postResponseDto);
}
