package ru.hl.socialnetwork.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.hl.socialnetwork.model.dto.request.dialog.DialogMessageRequestDto;
import ru.hl.socialnetwork.model.dto.response.DialogMessageResponseDto;

import java.util.List;

@RequestMapping("/api/dialog")
public interface DialogController {

  @GetMapping("/{userId}/list")
  ResponseEntity<List<DialogMessageResponseDto>> getDialog(@PathVariable Integer userId);

  @PostMapping("/{userId}/send")
  ResponseEntity<Void> sendMessage(@PathVariable Integer userId, @RequestBody DialogMessageRequestDto dialogMessageRequestDto);
}
