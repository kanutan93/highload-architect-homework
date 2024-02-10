package ru.hl.primaryservice.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.hl.primaryservice.model.dto.request.dialog.DialogMessageRequestDto;
import ru.hl.primaryservice.model.dto.response.DialogMessageResponseDto;

import java.util.List;

@FeignClient(name = "dialogServiceClient", url = "http://dialog-service:8080")
public interface DialogServiceClient {

  @GetMapping("/api/dialog/{currentUserId}/{userId}/list")
  ResponseEntity<List<DialogMessageResponseDto>> getDialog(@PathVariable Integer currentUserId, @PathVariable Integer userId);

  @PostMapping("/api/dialog/{currentUserId}/{userId}/send")
  ResponseEntity<Void> sendMessage(@PathVariable Integer currentUserId, @PathVariable Integer userId, @RequestBody DialogMessageRequestDto dialogMessageRequestDto);
}