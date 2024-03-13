package ru.hl.primaryservice.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.hl.primaryservice.controller.DialogController;
import ru.hl.primaryservice.external.CounterServiceClient;
import ru.hl.primaryservice.external.DialogServiceClient;
import ru.hl.primaryservice.model.dto.request.dialog.DialogMessageRequestDto;
import ru.hl.primaryservice.model.dto.response.DialogMessageResponseDto;
import ru.hl.primaryservice.model.dto.response.UnreadMessageCounterResponseDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DialogControllerImpl implements DialogController {

  private final DialogServiceClient dialogServiceClient;
  private final CounterServiceClient counterServiceClient;

  @Override
  public ResponseEntity<List<DialogMessageResponseDto>> getDialog(Integer currentUserId, Integer userId) {
    return dialogServiceClient.getDialog(currentUserId, userId);
  }

  @Override
  public ResponseEntity<Void> sendMessage(Integer currentUserId, Integer userId, DialogMessageRequestDto dialogMessageRequestDto) {
    return dialogServiceClient.sendMessage(currentUserId, userId, dialogMessageRequestDto);
  }

  @Override
  public ResponseEntity<List<UnreadMessageCounterResponseDto>> getUnreadMessageCounters(Integer currentUserId) {
    return counterServiceClient.getUnreadMessageCounters(currentUserId);
  }
}
