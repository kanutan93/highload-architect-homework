package ru.hl.dialogservice.service;

import ru.hl.dialogservice.model.dto.response.DialogMessageResponseDto;

import java.util.List;

public interface DialogService {

  List<DialogMessageResponseDto> getMessages(Integer currentUserId, Integer userId);

  void sendMessage(Integer currentUserId, Integer userId, String text);
}
