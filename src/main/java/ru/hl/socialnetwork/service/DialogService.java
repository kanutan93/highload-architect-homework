package ru.hl.socialnetwork.service;

import ru.hl.socialnetwork.model.dao.UserDao;
import ru.hl.socialnetwork.model.dto.response.DialogMessageResponseDto;

import java.util.List;

public interface DialogService {

  List<DialogMessageResponseDto> getMessages(Integer userId);

  List<DialogMessageResponseDto> getMessages(UserDao userDao, Integer userId);

  void sendMessage(Integer userId, String text);

  void sendMessage(UserDao userDao, Integer userId, String text);
}
