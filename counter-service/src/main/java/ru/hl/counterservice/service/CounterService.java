package ru.hl.counterservice.service;

import ru.hl.counterservice.model.dto.response.UnreadMessageCounterResponseDto;

import java.util.Set;

public interface CounterService {

  Set<UnreadMessageCounterResponseDto> getUnreadMessageCounters(Integer currentUserId);

  void incrementUnreadMessageCounter(Integer currentUserId, Integer userId);

  void clearUnreadMessageCounter(Integer currentUserId, Integer userId);
}
