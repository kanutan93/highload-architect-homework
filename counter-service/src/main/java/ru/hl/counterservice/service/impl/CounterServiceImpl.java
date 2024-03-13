package ru.hl.counterservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hl.counterservice.kafka.payload.UnreadMessageCounterPayload;
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
  private final KafkaTemplate<String, String> kafkaTemplate;
  private final CounterMapper counterMapper;
  private final ObjectMapper objectMapper;

  @Value("${kafka.delete-unread-message-counter-topic}")
  private String kafkaTopic;

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
  @SneakyThrows
  @Transactional
  public void incrementUnreadMessageCounter(Integer receiverUserId, Integer senderUserId) {
    log.info("Trying to increment unread message counter for receiver user with id: {} and sender user with id: {}", receiverUserId, senderUserId);

    try {
      Optional<CounterDao> unreadMessageCounterOptional = getCounter(receiverUserId, senderUserId);
      if (unreadMessageCounterOptional.isPresent()) {
        CounterDao unreadMessageCounter = unreadMessageCounterOptional.get();
        log.info("Unread message counter: {} for receiver user with id: {} and sender user with id: {} has been received successfully", unreadMessageCounter, receiverUserId, senderUserId);
        counterRepository.updateCounter(unreadMessageCounter.getId(), receiverUserId, senderUserId, unreadMessageCounter.getCount() + 1);
      } else {
        log.info("Unread message counter for receiver user with id: {} and sender user with id: {} not found. Creating new one...", receiverUserId, senderUserId);
        counterRepository.createCounter(receiverUserId, senderUserId, 1);
      }

      log.info("Unread message counter for receiver user with id: {} and sender user with id: {} has been incremented successfully", receiverUserId, senderUserId);
    } catch (Exception e) {
      log.error("Error on incrementing message counter", e);
      deleteUnreadMessageCounter(receiverUserId, senderUserId);
      UnreadMessageCounterPayload unreadMessageCounterPayload = new UnreadMessageCounterPayload(senderUserId, receiverUserId);
      kafkaTemplate.send(kafkaTopic, String.valueOf(senderUserId), objectMapper.writeValueAsString(unreadMessageCounterPayload));
    }
  }

  @Override
  @Transactional
  public void deleteUnreadMessageCounter(Integer receiverUserId, Integer senderUserId) {
    log.info("Trying to delete unread message counter for receiver user with id: {} and sender user with id: {}", receiverUserId, senderUserId);

    try {
      Optional<CounterDao> unreadMessageCounterOptional = getCounter(receiverUserId, senderUserId);
      if (unreadMessageCounterOptional.isPresent()) {
        CounterDao unreadMessageCounter = unreadMessageCounterOptional.get();
        counterRepository.deleteCounter(unreadMessageCounter.getId());
        log.info("Unread message counter for receiver user with id: {} and sender user with id: {} has been deleted successfully", receiverUserId, senderUserId);
      } else {
        log.warn("Unread message counter for receiver user with id: {} and sender user with id: {} not found", receiverUserId, senderUserId);
      }
    } catch (Exception e) {
      log.error("Error on deleting message counter", e);
    }
  }

  private Optional<CounterDao> getCounter(Integer receiverUserId, Integer senderUserId) {
    try {
      return ofNullable(counterRepository.getCounter(receiverUserId, senderUserId));
    } catch (DataRetrievalFailureException e) {
      return Optional.empty();
    }
  }
}
