package ru.hl.dialogservice.kafka.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.hl.dialogservice.kafka.payload.UnreadMessageCounterPayload;
import ru.hl.dialogservice.service.DialogService;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeleteUnreadMessageCounterKafkaListener {

  private final ObjectMapper objectMapper;
  private final DialogService dialogService;

  @KafkaListener(topics = "${kafka.delete-unread-message-counter-topic}")
  @SneakyThrows
  public void consume(ConsumerRecord<String, String> record) {
    String payload = record.value();
    log.info("New message received: {}", payload);

    var deleteUnreadMessageCounterPayload = objectMapper.readValue(payload, UnreadMessageCounterPayload.class);
    dialogService.deleteMessage(deleteUnreadMessageCounterPayload.getSenderUserId(), deleteUnreadMessageCounterPayload.getReceiverUserId());

    log.info("Message: {} has been handled successfully", payload);
  }
}
