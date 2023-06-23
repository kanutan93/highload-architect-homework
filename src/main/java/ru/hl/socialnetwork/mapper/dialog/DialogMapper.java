package ru.hl.socialnetwork.mapper.dialog;

import org.mapstruct.Mapper;
import ru.hl.socialnetwork.model.dao.MessageDao;
import ru.hl.socialnetwork.model.dto.response.DialogMessageResponseDto;

@Mapper
public interface DialogMapper {

  DialogMessageResponseDto toDialogMessageResponseDto(MessageDao messageDao);
}
