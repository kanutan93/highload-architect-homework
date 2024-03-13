package ru.hl.counterservice.service;

import ru.hl.counterservice.model.dto.response.UnreadMessageCounterResponseDto;

import java.util.List;

public interface CounterService {

  List<UnreadMessageCounterResponseDto> getUnreadMessageCounters(Integer currentUserId);

  void incrementUnreadMessageCounter(Integer receiverUserId, Integer senderUserId);

  void deleteUnreadMessageCounter(Integer receiverUserId, Integer senderUserId);
}
