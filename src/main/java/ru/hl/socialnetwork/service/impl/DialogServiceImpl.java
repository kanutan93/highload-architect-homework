package ru.hl.socialnetwork.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.hl.socialnetwork.aop.DialogConnection;
import ru.hl.socialnetwork.mapper.dialog.DialogMapper;
import ru.hl.socialnetwork.model.dao.MessageDao;
import ru.hl.socialnetwork.model.dao.UserDao;
import ru.hl.socialnetwork.model.dto.response.DialogMessageResponseDto;
import ru.hl.socialnetwork.repository.DialogRepository;
import ru.hl.socialnetwork.repository.UserRepository;
import ru.hl.socialnetwork.service.DialogService;

import java.util.List;
import java.util.stream.Collectors;

import static ru.hl.socialnetwork.util.CurrentUserUtil.getCurrentUser;

@Slf4j
@Service
public class DialogServiceImpl implements DialogService {

  private final DialogMapper dialogMapper;
  private final DialogRepository dialogRepository;
  private final UserRepository userRepository;
  private final DialogService dialogService;

  public DialogServiceImpl(DialogMapper dialogMapper, DialogRepository dialogRepository, UserRepository userRepository, @Lazy DialogService dialogService) {
    this.dialogMapper = dialogMapper;
    this.dialogRepository = dialogRepository;
    this.userRepository = userRepository;
    this.dialogService = dialogService;
  }

  @Override
  @Transactional(readOnly = true)
  public List<DialogMessageResponseDto> getMessages(Integer userId) {
    User currentUser = getCurrentUser();
    UserDao userDao = userRepository.getByEmail(currentUser.getUsername());
    return dialogService.getMessages(userDao, userId);
  }

  @Override
  @DialogConnection
  @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
  public List<DialogMessageResponseDto> getMessages(UserDao userDao, Integer userId) {
    log.info("Try to get dialog messages for user with id: {} and current user with id: {}", userId, userDao.getId());

    Integer dialogId = dialogRepository.getDialogId(userDao.getId(), userId);
    var dialogMessages = dialogRepository.getMessages(dialogId).stream()
        .map(dialogMapper::toDialogMessageResponseDto)
        .collect(Collectors.toList());
    log.info("Dialog messages: {} for user with id: {} and current user with id: {} has been received successfully", dialogMessages, userId, userDao.getId());

    return dialogMessages;
  }

  @Override
  @Transactional
  public void sendMessage(Integer userId, String text) {
    User currentUser = getCurrentUser();
    UserDao userDao = userRepository.getByEmail(currentUser.getUsername());
    dialogService.sendMessage(userDao, userId, text);
  }

  @Override
  @DialogConnection
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void sendMessage(UserDao userDao, Integer userId, String text) {
    log.info("Try to send message to user with id: {} from current user with id: {} ", userId);

    Integer dialogId = dialogRepository.getDialogId(userDao.getId(), userId);
    if (dialogId == null) {
      dialogId = dialogRepository.createDialog(userDao.getId(), userId);
    }

    MessageDao messageDao = new MessageDao();
    messageDao.setFrom(userDao.getId());
    messageDao.setTo(userId);
    messageDao.setText(text);
    messageDao.setDialogId(dialogId);

    dialogRepository.createMessage(messageDao);

    log.info("Message: {} has been sent successfully to user with id: {} from current user with id: {} ", userId, userDao.getId());
  }
}
