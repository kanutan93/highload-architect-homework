package ru.hl.socialnetwork.mapper.post;

import org.mapstruct.Mapper;
import ru.hl.socialnetwork.model.dao.PostDao;
import ru.hl.socialnetwork.model.dto.response.PostResponseDto;

@Mapper
public interface PostMapper {

  PostResponseDto toPostResponseDto(PostDao postDao);
}
