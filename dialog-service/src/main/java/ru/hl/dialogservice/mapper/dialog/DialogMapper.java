package ru.hl.dialogservice.mapper.dialog;

import org.mapstruct.Mapper;
import ru.hl.dialogservice.model.dao.MessageDao;
import ru.hl.dialogservice.model.dto.response.DialogMessageResponseDto;

@Mapper
public interface DialogMapper {

  DialogMessageResponseDto toDialogMessageResponseDto(MessageDao messageDao);
}
