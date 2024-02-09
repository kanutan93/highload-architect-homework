package ru.hl.coreservice.mapper.post;

import org.mapstruct.Mapper;
import ru.hl.coreservice.kafka.payload.PostPayload;
import ru.hl.coreservice.model.dao.PostDao;
import ru.hl.coreservice.model.dto.response.PostResponseDto;

@Mapper
public interface PostMapper {

  PostResponseDto toPostResponseDto(PostDao postDao);

  PostPayload toPostPayload(Integer receiverUserId, PostPayload.Action action, PostResponseDto postResponseDto);
}
