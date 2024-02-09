package ru.hl.coreservice.mapper.dialog;

import org.mapstruct.Mapper;
import ru.hl.coreservice.model.dao.MessageDao;
import ru.hl.coreservice.model.dto.response.DialogMessageResponseDto;

@Mapper
public interface DialogMapper {

  DialogMessageResponseDto toDialogMessageResponseDto(MessageDao messageDao);
}
