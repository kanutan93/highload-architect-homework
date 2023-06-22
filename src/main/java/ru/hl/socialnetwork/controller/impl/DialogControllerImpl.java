package ru.hl.socialnetwork.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.hl.socialnetwork.controller.DialogController;
import ru.hl.socialnetwork.model.dto.request.dialog.DialogMessageRequestDto;
import ru.hl.socialnetwork.model.dto.response.DialogMessageResponseDto;
import ru.hl.socialnetwork.service.DialogService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DialogControllerImpl implements DialogController {

  private final DialogService dialogService;

  @Override
  public ResponseEntity<List<DialogMessageResponseDto>> getDialog(Integer userId) {
    List<DialogMessageResponseDto> dialog = dialogService.getMessages(userId);
    return ResponseEntity.ok(dialog);
  }

  @Override
  public ResponseEntity<Void> sendMessage(Integer userId, DialogMessageRequestDto dialogMessageRequestDto) {
    dialogService.sendMessage(userId, dialogMessageRequestDto.getText());
    return ResponseEntity.ok().build();
  }
}
