package ru.hl.counterservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.PartialUpdate;
import org.springframework.data.redis.core.RedisKeyValueTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hl.counterservice.mapper.counter.CounterMapper;
import ru.hl.counterservice.model.dao.CounterDao;
import ru.hl.counterservice.model.dto.response.UnreadMessageCounterResponseDto;
import ru.hl.counterservice.service.CounterService;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Slf4j
@Service
@RequiredArgsConstructor
public class CounterServiceImpl implements CounterService {

  private final RedisTemplate<Integer, CounterDao> redisTemplate;
  private final CounterMapper counterMapper;
  private final RedisKeyValueTemplate redisKeyValueTemplate;

  @Override
  @Transactional(readOnly = true)
  public Set<UnreadMessageCounterResponseDto> getUnreadMessageCounters(Integer currentUserId) {
    log.info("Trying to get unread message counters for user with id: {}", currentUserId);

    SetOperations<Integer, CounterDao> opsForSet = redisTemplate.opsForSet();
    Set<CounterDao> counterDaoSet = ofNullable(opsForSet.members(currentUserId))
        .orElse(new HashSet<>());
    Set<UnreadMessageCounterResponseDto> unreadMessageCounters = counterDaoSet
        .stream()
        .map(counterMapper::toUnreadMessageCounterResponseDto)
        .collect(Collectors.toSet());

    log.info("Unread message counters: {} for user with id: {} have been received successfully", unreadMessageCounters, currentUserId);
    return unreadMessageCounters;
  }

  @Override
  @Transactional
  public void incrementUnreadMessageCounter(Integer currentUserId, Integer userId) {
    log.info("Trying to increment message counters for current user with id: {} and user with id: {}", currentUserId, userId);

    SetOperations<Integer, CounterDao> opsForSet = redisKeyValueTemplate.opsForSet();
    PartialUpdate<CounterDao> partialUpdate = PartialUpdate.newPartialUpdate(userId, CounterDao.class);
    Set<CounterDao> counterDaoSet = ofNullable(opsForSet.members(currentUserId))
        .orElse(new HashSet<>());
    if (counterDaoSet.isEmpty()) {
      opsForSet.add(currentUserId, new CounterDao(userId, 1));
    } else {
     redisKeyValueTemplate.update(partialUpdate);
    }

    log.info("Unread message counter for current user with id: {} and user with id: {} has been incremented successfully", currentUserId, userId);
  }

  @Override
  @Transactional(readOnly = true)
  public void clearUnreadMessageCounter(Integer currentUserId, Integer userId) {

  }
}
