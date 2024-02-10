package ru.hl.dialogservice.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.hl.dialogservice.controller.DialogController;
import ru.hl.dialogservice.model.dto.request.DialogMessageRequestDto;
import ru.hl.dialogservice.model.dto.response.DialogMessageResponseDto;
import ru.hl.dialogservice.service.DialogService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DialogControllerImpl implements DialogController {

  private final DialogService dialogService;

  @Override
  public ResponseEntity<List<DialogMessageResponseDto>> getDialog(Integer currentUserId, Integer userId) {
    List<DialogMessageResponseDto> dialog = dialogService.getMessages(currentUserId, userId);
    return ResponseEntity.ok(dialog);
  }

  @Override
  public ResponseEntity<Void> sendMessage(Integer currentUserId, Integer userId, DialogMessageRequestDto dialogMessageRequestDto) {
    dialogService.sendMessage(currentUserId, userId, dialogMessageRequestDto.getText());
    return ResponseEntity.ok().build();
  }
}
