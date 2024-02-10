package ru.hl.dialogservice.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.hl.dialogservice.mapper.dialog.DialogMapper;
import ru.hl.dialogservice.model.dao.MessageDao;
import ru.hl.dialogservice.model.dto.response.DialogMessageResponseDto;
import ru.hl.dialogservice.repository.DialogRepository;
import ru.hl.dialogservice.service.DialogService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DialogServiceImpl implements DialogService {

  private final DialogMapper dialogMapper;
  private final DialogRepository dialogRepository;

  public DialogServiceImpl(DialogMapper dialogMapper, DialogRepository dialogRepository) {
    this.dialogMapper = dialogMapper;
    this.dialogRepository = dialogRepository;
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
  public List<DialogMessageResponseDto> getMessages(Integer currentUserId, Integer userId) {
    log.info("Try to get dialog messages for user with id: {} and current user with id: {}", userId, currentUserId);

    Integer dialogId = dialogRepository.getDialogId(currentUserId, userId);
    var dialogMessages = dialogRepository.getMessages(dialogId).stream()
        .map(dialogMapper::toDialogMessageResponseDto)
        .collect(Collectors.toList());
    log.info("Dialog messages: {} for user with id: {} and current user with id: {} has been received successfully", dialogMessages, userId, currentUserId);

    return dialogMessages;
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void sendMessage(Integer currentUserId, Integer userId, String text) {
    log.info("Try to send message to user with id: {} from current user with id: {} ", userId);

    Integer dialogId = dialogRepository.getDialogId(currentUserId, userId);
    if (dialogId == null) {
      dialogId = dialogRepository.createDialog(currentUserId, userId);
    }

    MessageDao messageDao = new MessageDao();
    messageDao.setFrom(currentUserId);
    messageDao.setTo(userId);
    messageDao.setText(text);
    messageDao.setDialogId(dialogId);

    dialogRepository.createMessage(messageDao);

    log.info("Message: {} has been sent successfully to user with id: {} from current user with id: {} ", userId, currentUserId);
  }
}
