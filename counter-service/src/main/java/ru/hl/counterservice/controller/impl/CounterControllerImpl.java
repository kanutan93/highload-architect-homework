package ru.hl.counterservice.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.hl.counterservice.controller.CounterController;
import ru.hl.counterservice.model.dto.response.UnreadMessageCounterResponseDto;
import ru.hl.counterservice.service.CounterService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CounterControllerImpl implements CounterController {

  private final CounterService counterService;

  @Override
  public ResponseEntity<List<UnreadMessageCounterResponseDto>> getUnreadMessageCounters(Integer currentUserId) {
    List<UnreadMessageCounterResponseDto> unreadMessageCounters = counterService.getUnreadMessageCounters(currentUserId);
    return ResponseEntity.ok(unreadMessageCounters);
  }
}
