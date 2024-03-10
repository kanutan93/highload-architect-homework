package ru.hl.counterservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.hl.counterservice.model.dto.response.UnreadMessageCounterResponseDto;

import java.util.List;

@RequestMapping("/api/counter")
public interface CounterController {

  @GetMapping("/{currentUserId}")
  ResponseEntity<List<UnreadMessageCounterResponseDto>> getUnreadMessageCounters(@PathVariable Integer currentUserId);
}
