package ru.hl.counterservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.hl.counterservice.model.dto.response.UnreadMessageCounterResponseDto;

import java.util.Set;

@RequestMapping("/api/counter")
public interface CounterController {

  @GetMapping("/{currentUserId}")
  ResponseEntity<Set<UnreadMessageCounterResponseDto>> getUnreadMessageCounters(@PathVariable Integer currentUserId);
}
