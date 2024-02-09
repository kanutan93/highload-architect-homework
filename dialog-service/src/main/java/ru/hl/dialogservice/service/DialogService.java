package ru.hl.dialogservice.service;

import ru.hl.coreservice.model.dao.UserDao;
import ru.hl.coreservice.model.dto.response.DialogMessageResponseDto;
import ru.hl.dialogservice.model.dto.response.DialogMessageResponseDto;

import java.util.List;

public interface DialogService {

  List<DialogMessageResponseDto> getMessages(Integer userId);

  List<DialogMessageResponseDto> getMessages(UserDao userDao, Integer userId);

  void sendMessage(Integer userId, String text);

  void sendMessage(UserDao userDao, Integer userId, String text);
}
