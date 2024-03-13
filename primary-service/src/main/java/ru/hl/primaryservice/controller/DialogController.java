package ru.hl.primaryservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.hl.primaryservice.model.dto.request.dialog.DialogMessageRequestDto;
import ru.hl.primaryservice.model.dto.response.DialogMessageResponseDto;
import ru.hl.primaryservice.model.dto.response.UnreadMessageCounterResponseDto;

import java.util.List;

@RequestMapping("/api/dialog")
public interface DialogController {

  @GetMapping("/{currentUserId}/{userId}/list")
  ResponseEntity<List<DialogMessageResponseDto>> getDialog(@PathVariable Integer currentUserId, @PathVariable Integer userId);

  @PostMapping("/{currentUserId}/{userId}/send")
  ResponseEntity<Void> sendMessage(@PathVariable Integer currentUserId, @PathVariable Integer userId, @RequestBody DialogMessageRequestDto dialogMessageRequestDto);

  @GetMapping("/undead-messages-counter/{currentUserId}")
  ResponseEntity<List<UnreadMessageCounterResponseDto>> getUnreadMessageCounters(@PathVariable Integer currentUserId);
}
