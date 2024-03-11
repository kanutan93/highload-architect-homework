package ru.hl.counterservice.kafka.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.hl.counterservice.kafka.payload.UnreadMessageCounterPayload;
import ru.hl.counterservice.service.CounterService;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateUnreadMessageCounterKafkaListener {

  private final ObjectMapper objectMapper;
  private final CounterService counterService;


  @KafkaListener(topics = "${kafka.create-unread-message-counter-topic}")
  @SneakyThrows
  public void consume(ConsumerRecord<String, String> record) {
    String payload = record.value();
    log.info("New message received: {}", payload);

    UnreadMessageCounterPayload unreadMessageCounterPayload = objectMapper.readValue(payload, UnreadMessageCounterPayload.class);
    counterService.incrementUnreadMessageCounter(unreadMessageCounterPayload.getReceiverUserId(), unreadMessageCounterPayload.getSenderUserId());
  }
}
