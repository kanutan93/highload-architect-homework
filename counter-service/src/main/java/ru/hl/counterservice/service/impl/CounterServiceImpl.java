package ru.hl.counterservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hl.counterservice.mapper.counter.CounterMapper;
import ru.hl.counterservice.model.dao.CounterDao;
import ru.hl.counterservice.model.dto.response.UnreadMessageCounterResponseDto;
import ru.hl.counterservice.repository.CounterRepository;
import ru.hl.counterservice.service.CounterService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Slf4j
@Service
@RequiredArgsConstructor
public class CounterServiceImpl implements CounterService {

  private final CounterRepository counterRepository;
  private final CounterMapper counterMapper;

  @Override
  @Transactional(readOnly = true)
  public List<UnreadMessageCounterResponseDto> getUnreadMessageCounters(Integer currentUserId) {
    log.info("Trying to get unread message counters for user with id: {}", currentUserId);

    List<UnreadMessageCounterResponseDto> unreadMessageCounters = ofNullable(counterRepository.getCounters(currentUserId))
        .orElse(new ArrayList<>())
        .stream()
        .map(counterMapper::toUnreadMessageCounterResponseDto)
        .collect(Collectors.toList());

    log.info("Unread message counters: {} for user with id: {} have been received successfully", unreadMessageCounters, currentUserId);
    return unreadMessageCounters;
  }

  @Override
  @Transactional
  public void incrementUnreadMessageCounter(Integer currentUserId, Integer userId) {
    log.info("Trying to increment unread message counter for current user with id: {} and user with id: {}", currentUserId, userId);

    Optional<CounterDao> unreadMessageCounterOptional = ofNullable(counterRepository.getCounter(currentUserId, userId));
    if (unreadMessageCounterOptional.isPresent()) {
      CounterDao unreadMessageCounter = unreadMessageCounterOptional.get();
      log.info("Unread message counter: {} for current user with id: {} and user with id: {} has been received successfully", unreadMessageCounter, currentUserId, userId);
      counterRepository.updateCounter(unreadMessageCounter.getId(), currentUserId, userId, unreadMessageCounter.getCount() + 1);
    } else {
      log.info("Unread message counter for current user with id: {} and user with id: {} not found. Creating new one...", currentUserId, userId);
      counterRepository.createCounter(currentUserId, userId, 1);
    }

    log.info("Unread message counter for current user with id: {} and user with id: {} has been incremented successfully", currentUserId, userId);
  }

  @Override
  public void deleteUnreadMessageCounter(Integer currentUserId, Integer userId) {
    log.info("Trying to delete unread message counter for current user with id: {} and user with id: {}", currentUserId, userId);

    Optional<CounterDao> unreadMessageCounterOptional = ofNullable(counterRepository.getCounter(currentUserId, userId));
    if (unreadMessageCounterOptional.isPresent()) {
      CounterDao unreadMessageCounter = unreadMessageCounterOptional.get();
      counterRepository.deleteCounter(unreadMessageCounter.getId());
      log.info("Unread message counter for current user with id: {} and user with id: {} has been deleted successfully", currentUserId, userId);
    } else {
      log.warn("Unread message counter for current user with id: {} and user with id: {} not found", currentUserId, userId);
    }
  }
}
