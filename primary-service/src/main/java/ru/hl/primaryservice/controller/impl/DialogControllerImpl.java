package ru.hl.primaryservice.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.hl.primaryservice.controller.DialogController;
import ru.hl.primaryservice.external.DialogServiceClient;
import ru.hl.primaryservice.model.dto.request.dialog.DialogMessageRequestDto;
import ru.hl.primaryservice.model.dto.response.DialogMessageResponseDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DialogControllerImpl implements DialogController {

  private final DialogServiceClient dialogServiceClient;

  @Override
  public ResponseEntity<List<DialogMessageResponseDto>> getDialog(Integer currentUserId, Integer userId) {
    return dialogServiceClient.getDialog(currentUserId, userId);
  }

  @Override
  public ResponseEntity<Void> sendMessage(Integer currentUserId, Integer userId, DialogMessageRequestDto dialogMessageRequestDto) {
    return dialogServiceClient.sendMessage(currentUserId, userId, dialogMessageRequestDto);
  }
}
