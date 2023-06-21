package ru.hl.socialnetwork.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hl.socialnetwork.aop.DialogConnection;
import ru.hl.socialnetwork.mapper.dialog.DialogMapper;
import ru.hl.socialnetwork.model.dao.DialogDao;
import ru.hl.socialnetwork.model.dao.UserDao;
import ru.hl.socialnetwork.model.dto.response.DialogMessageResponseDto;
import ru.hl.socialnetwork.repository.DialogRepository;
import ru.hl.socialnetwork.repository.UserRepository;
import ru.hl.socialnetwork.service.DialogService;
import ru.hl.socialnetwork.util.CurrentUserUtil;

import java.util.List;
import java.util.stream.Collectors;

import static ru.hl.socialnetwork.util.CurrentUserUtil.getCurrentUser;

@Slf4j
@Service
@RequiredArgsConstructor
public class DialogServiceImpl implements DialogService {

  private final DialogMapper dialogMapper;
  private final DialogRepository dialogRepository;
  private final UserRepository userRepository;

  @Override
  @DialogConnection
  @Transactional(readOnly = true)
  public List<DialogMessageResponseDto> getDialog(Integer userId) {
    User currentUser = getCurrentUser();
    UserDao userDao = userRepository.getByEmail(currentUser.getUsername());
    log.info("Try to get dialog messages for user with id: {} and current user with id: {}", userId, userDao.getId());

    var dialogMessages = dialogRepository.getDialogMessages(userDao.getId(), userId).stream()
        .map(dialogMapper::toDialogMessageResponseDto)
        .collect(Collectors.toList());
    log.info("Dialog messages: {} for user with id: {} and current user with id: {} has been received successfully", dialogMessages, userId, userDao.getId());

    return dialogMessages;
  }

  @Override
  @DialogConnection
  @Transactional
  public void sendMessage(Integer userId, String text) {
    User currentUser = getCurrentUser();
    UserDao userDao = userRepository.getByEmail(currentUser.getUsername());
    log.info("Try to send message to user with id: {} from current user with id: {} ", userId);

    DialogDao dialogDao = new DialogDao();
    dialogDao.setFrom(userDao.getId());
    dialogDao.setTo(userId);
    dialogDao.setText(text);

    dialogRepository.createDialogMessage(dialogDao);

    log.info("Message: {} has been sent successfully to user with id: {} from current user with id: {} ", userId, userDao.getId());

  }
}
