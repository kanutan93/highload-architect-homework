package ru.hl.dialogservice.service;

import lombok.SneakyThrows;
import org.springframework.transaction.annotation.Transactional;
import ru.hl.dialogservice.model.dto.response.DialogMessageResponseDto;

import java.util.List;

public interface DialogService {

  List<DialogMessageResponseDto> getMessages(Integer currentUserId, Integer userId);

  void sendMessage(Integer senderUserId, Integer receiverUserId, String text);

  void deleteMessage(Integer senderUserId, Integer receiverUserId);
}
