package ru.hl.dialogservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hl.dialogservice.kafka.payload.UnreadMessageCounterPayload;
import ru.hl.dialogservice.mapper.dialog.DialogMapper;
import ru.hl.dialogservice.model.dao.DialogDao;
import ru.hl.dialogservice.model.dao.MessageDao;
import ru.hl.dialogservice.model.dto.response.DialogMessageResponseDto;
import ru.hl.dialogservice.repository.DialogRepository;
import ru.hl.dialogservice.repository.MessageRepository;
import ru.hl.dialogservice.service.DialogService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DialogServiceImpl implements DialogService {

  private final DialogMapper dialogMapper;
  private final DialogRepository dialogRepository;
  private final MessageRepository messageRepository;
  private final KafkaTemplate<String, String> kafkaTemplate;
  private final ObjectMapper objectMapper;

  @Value("${kafka.create-unread-message-counter-topic}")
  private String kafkaTopic;

  @Override
  @Transactional(readOnly = true)
  public List<DialogMessageResponseDto> getMessages(Integer currentUserId, Integer userId) {
    log.info("Try to get dialog messages for user with id: {} and current user with id: {}", userId, currentUserId);

    List<DialogDao> dialogIds = dialogRepository.getDialogId(currentUserId, userId);
    if (dialogIds.size() == 0) {
      return new ArrayList<>();
    }
    Integer dialogId = dialogIds.get(0).getId();
    var dialogMessages = messageRepository.getMessages(dialogId).stream()
        .map(dialogMapper::toDialogMessageResponseDto)
        .collect(Collectors.toList());
    log.info("Dialog messages: {} for user with id: {} and current user with id: {} has been received successfully", dialogMessages, userId, currentUserId);

    return dialogMessages;
  }

  @Override
  @SneakyThrows
  @Transactional
  public void sendMessage(Integer senderUserId, Integer receiverUserId, String text) {
    log.info("Try to send message to user with id: {} from current user with id: {} ", receiverUserId);

    List<DialogDao> dialogIds = dialogRepository.getDialogId(senderUserId, receiverUserId);
    Integer dialogId = dialogIds.size() == 0 ? null : dialogIds.get(0).getId();
    if (dialogId == null) {
      dialogId = dialogRepository.createDialog(senderUserId, receiverUserId).getId();
    }

    MessageDao messageDao = new MessageDao();
    messageDao.setFrom(senderUserId);
    messageDao.setTo(receiverUserId);
    messageDao.setText(text);
    messageDao.setDialogId(dialogId);

    messageRepository.createMessage(senderUserId, receiverUserId, text, dialogId);
    UnreadMessageCounterPayload unreadMessageCounterPayload = new UnreadMessageCounterPayload(senderUserId, receiverUserId);
    kafkaTemplate.send(kafkaTopic, String.valueOf(senderUserId), objectMapper.writeValueAsString(unreadMessageCounterPayload));

    log.info("Message: {} has been sent successfully to user with id: {} from current user with id: {} ", receiverUserId, senderUserId);
  }

  @Override
  @SneakyThrows
  @Transactional
  public void deleteMessage(Integer senderUserId, Integer receiverUserId) {
    log.info("Trying to delete message to user with id: {} from current user with id: {} ", receiverUserId, senderUserId);

    MessageDao messageDao = messageRepository.getMessage(senderUserId, receiverUserId);
    messageRepository.deleteMessage(messageDao.getId());

    log.info("Message with id: {} has been deleted successfully to user with id: {} from current user with id: {} ", messageDao.getId(), receiverUserId, senderUserId);
  }
}
