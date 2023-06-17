package ru.hl.socialnetwork.service;

import ru.hl.socialnetwork.model.dto.response.DialogMessageResponseDto;

import java.util.List;

public interface DialogService {

  List<DialogMessageResponseDto> getDialog(Integer userId);

  void sendMessage(Integer userId, String text);
}
