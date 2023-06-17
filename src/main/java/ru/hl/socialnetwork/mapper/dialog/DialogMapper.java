package ru.hl.socialnetwork.mapper.dialog;

import org.mapstruct.Mapper;
import ru.hl.socialnetwork.kafka.payload.PostPayload;
import ru.hl.socialnetwork.model.dao.DialogDao;
import ru.hl.socialnetwork.model.dao.PostDao;
import ru.hl.socialnetwork.model.dto.response.DialogMessageResponseDto;
import ru.hl.socialnetwork.model.dto.response.PostResponseDto;

@Mapper
public interface DialogMapper {

  DialogMessageResponseDto toDialogMessageResponseDto(DialogDao dialogDao);
}
